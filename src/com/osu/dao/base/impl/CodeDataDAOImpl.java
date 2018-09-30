package com.osu.dao.base.impl;

import java.io.FileNotFoundException; 
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.naming.NamingException;

import com.osu.common.constants.SqlConstants;
import com.osu.dao.base.interfaces.CodeDataDAO;
import com.osu.database.connection.DBConnectionFactory;
import com.osu.database.pojo.QuestionOptionsPojo;
import com.osu.database.pojo.QuestionPojo;
import com.osu.database.pojo.ResponsePojo;
import com.osu.database.pojo.SubQuestResponsePojo;

public class CodeDataDAOImpl implements CodeDataDAO {

	Connection connection = null;

	@Override
	public Connection getConnection() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException, NamingException {
		
		if ((connection == null) || (connection.isClosed())) {
			connection = DBConnectionFactory.getConnection();
		}
		
		return connection;
	}

	public ArrayList<QuestionPojo> getFeedbackQuestions() throws Exception {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connect = null;
		Statement statement = null;
		QuestionPojo question = null;
		QuestionOptionsPojo questionOption = null;
		ArrayList<QuestionPojo> questionList = new ArrayList<QuestionPojo>();
		ArrayList<QuestionOptionsPojo> questionOptionList = null;
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.GET_QUESTIONS);
			resultSet = preparedStatement.executeQuery();
			int tempQuestId = -99999;
			
			while (resultSet.next()) {
				
				if (tempQuestId == resultSet.getInt("questionid")) {
					
					// **WHEN INTO IF ALWAYS A NEW SUB QUESTION
					questionOption = new QuestionOptionsPojo();
					questionOption.setQuestionOptionId(resultSet.getInt("questoptionid"));
					questionOption.setOptionSerialNo(resultSet.getInt("quesopt.serialno"));
					questionOption.setOptionText(resultSet.getString("optiontext"));
					questionOption.setForQuestion(resultSet.getInt("forquestion"));
					questionOption.setOptionType(resultSet.getString("optionType")==null?"":resultSet.getString("optionType"));

					questionOptionList.add(questionOption);
					
				} else {
					
					// **WHEN INTO ELSE ALWAYS A NEW PRIMARY QUESTION---
					//**SO ALWAYS ADD SUBQUEST LIST INTO PRIMARY QUEST
					//**AND PRIMARY QUEST INTO QUESTIONS_LIST WHEN INTO ELSE
					if (tempQuestId != -99999) {
						question.setQuestionOptions(questionOptionList);
						questionOptionList = null;
						questionList.add(question);
						question = null;
					}
					
					question = new QuestionPojo();
					question.setQuestionId(resultSet.getInt("questionid"));
					tempQuestId = resultSet.getInt("questionid");
					question.setSerialNo(resultSet.getInt("serialno"));
					question.setQuestionType(resultSet.getString("questiontype"));
					question.setQuestionText(resultSet.getString("questiontext"));
					System.out.println("IF SUB QUESTION EXISTS--"+resultSet.getInt("questoptionid"));
					//Add constraint in db primary key>0
					
					if (0 != resultSet.getInt("questoptionid")) {
						//IF SUBQUESTION EXISTS
						questionOption = new QuestionOptionsPojo();
						questionOptionList = new ArrayList<QuestionOptionsPojo>();
						questionOption.setQuestionOptionId(resultSet.getInt("questoptionid"));
						questionOption.setOptionSerialNo(resultSet.getInt("quesopt.serialno"));
						questionOption.setOptionText(resultSet.getString("optiontext"));
						questionOption.setForQuestion(resultSet.getInt("forquestion"));
						questionOption.setOptionType(resultSet.getString("optionType")==null?"":resultSet.getString("optionType"));

						questionOptionList.add(questionOption);
					}
					
				}//ELSE ENDS

			}//WHILE ENDS
			
			question.setQuestionOptions(questionOptionList);
			questionList.add(question);

		} catch (Exception e) {
			
			System.out.println("ERROR IN CodeDataDAOImpl METHOD--getFeedbackQuestions  -->" + e.getMessage());
			e.printStackTrace();
			throw e;
			
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return questionList;
	}
	
	public boolean insertResponse(ArrayList<ResponsePojo> userResponseList) throws Exception {
		PreparedStatement preparedStatement = null;
		Connection connect = null;
		Statement statement = null;
		boolean retFlag = false;
		ResultSet resultSet = null;
	
		try {
			
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.CAPTURE_RESPONSE);
			connect.setAutoCommit(false);
			Iterator<ResponsePojo> iterator = userResponseList.iterator();
			
			while(iterator.hasNext()){
				
				ResponsePojo singleResponse = (ResponsePojo)iterator.next();
				System.out.println("-->PRIMARY QUESTION-"+singleResponse.getQuestionId());
				preparedStatement.setString(1, singleResponse.getUserName());
				preparedStatement.setInt(2, singleResponse.getQuestionId());
				System.out.println("-------->has options-"+(singleResponse.getQuestionOptions()!=null));
				preparedStatement.setString(7, singleResponse.getAnonymous());
				System.out.println("-------->anonymous-"+singleResponse.getAnonymous());
				
				if(singleResponse.getQuestionOptions()!=null){
					ArrayList<SubQuestResponsePojo> optionResponseList = singleResponse.getQuestionOptions();
					Iterator<SubQuestResponsePojo> optionsIterator = optionResponseList.iterator();
					
					while(optionsIterator.hasNext()){
						SubQuestResponsePojo singleOption = (SubQuestResponsePojo)optionsIterator.next();
							
						preparedStatement.setInt(3, singleOption.getQuestionOptionId());
						System.out.println("-------------->questionOptionId-"+singleOption.getQuestionOptionId());

						preparedStatement.setInt(4, singleOption.getRating());
						System.out.println("---------------------->optionRating-"+singleOption.getRating());

						preparedStatement.setString(5, singleOption.getResponseText());
						System.out.println("---------------------->responseText-"+singleOption.getResponseText());

						preparedStatement.setString(6, null);
						System.out.println("---------------------->selectedOption-null");
						
						int executeUpdate = preparedStatement.executeUpdate();
						if (executeUpdate > 0) {
							retFlag = true;
						}
						else{
							retFlag = false;
						}
						
					}
					
				}else{
						preparedStatement.setInt(3, 0);
					
						preparedStatement.setInt(4, singleResponse.getRating());
						System.out.println("-------->rating-"+singleResponse.getRating());

						preparedStatement.setString(5, singleResponse.getResponseText());
						System.out.println("-------->responseText-"+singleResponse.getResponseText());

						preparedStatement.setString(6, singleResponse.getOptionR()!=null ? singleResponse.getOptionR():(singleResponse.getOptionRT()!=null ? singleResponse.getOptionRT():singleResponse.getOptionR2()));
						System.out.println("-------->optionR-"+singleResponse.getOptionR());
						System.out.println("-------->optionR2-"+singleResponse.getOptionR2());
						//NOTE: OPTION_R, OPTION_R2 AND OPTION_RT MAP TO SAME DB COLUMN SELECTED_OPTION
						System.out.println("-------->optionRT-"+singleResponse.getOptionRT());
						
						int executeUpdate = preparedStatement.executeUpdate();
						if (executeUpdate > 0) {
							retFlag = true;
						}
						else{
							retFlag = false;
						}
				}
			}
			connect.commit();
		} catch (Exception e) {
			
			System.out.println("ERROR IN CodeDataDAOImpl METHOD--insertResponse  -->"+e.getMessage());
			retFlag = false;
			e.printStackTrace();
			throw e;
			
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return retFlag;
	}
	
	
}
