package com.osu.common.constants;

public interface SqlConstants {

	String GET_QUESTIONS = "select questemp.questionid,questemp.serialno,questemp.questiontype,questemp.questiontext,"
			+ "quesopt.questoptionid,quesopt.serialno,quesopt.optiontext,quesopt.forquestion,quesopt.optionType " + "from questionstemplate questemp "
			+ "left outer join questionoptions quesopt on questemp.questionid = quesopt.forquestion order by questemp.serialno";

	String CAPTURE_RESPONSE = "INSERT INTO feedbackcapture(username,questionid,questoptionid,rating,comments,selectedoption,anonymous,created_date_time) VALUES(?,?,?,?,?,?,?,now())";

}
