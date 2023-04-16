package vttp.backend.repository;

public class SQLQueries {

    //Find user by email in USER
    public static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM USERS WHERE EMAIL=?";

    //Register user in USER
    public static final String SQL_REGISTER_USER = "INSERT INTO USERS (NAME, EMAIL, PASSWORD, ROLE) VALUES (?,?,?,?)";

    //Register day in DAYS
    public static final String SQL_REGISTER_DAY = "INSERT INTO DAYS (DAY_ID, EMAIL, PNL, DAY) VALUES (?,?,?,?)";

    //Find pnl via day_id in DAYS
    public static final String SQL_FIND_PNL_BY_DAY_ID = "SELECT PNL FROM DAYS WHERE DAY_ID=?";

    //Update the entry after editing the pnl in DAYS
    public static final String SQL_UPDATE_PNL = "UPDATE DAYS SET PNL=? WHERE DAY_ID=?";

    //Given the email (taken from jwt) and a period of time between XX date and YY date (to fill the calender), 
    //get the Day object (day_id, pnl, date) from DAYS to be formed as a List<Day> later
    public static final String SQL_GET_DAY_BY_EMAIL = "SELECT DAY_ID, PNL, DAY FROM DAYS WHERE EMAIL=? AND DAY BETWEEN ? AND ?";

    //Get the day_id using email and day in DAYS.
    //If day_id is present, send it to teh frontend and tie it to the journal entry. Else, create a new one when the journal is sent over 
    public static final String SQL_GET_DAY_ID_BY_EMAIL_AND_DAY = "SELECT DAY_ID FROM DAYS WHERE EMAIL=? AND DAY=?";
 
    //Upate the pnl when a Journal entry is DELETED in DAYS
    public static final String SQL_UPDATE_PNL_WHEN_JOURNAL_IS_DELETED = "UPDATE DAYS SET PNL = PNL-? WHERE DAY_ID=?";

    //Delete Day entry from Days
    public static final String SQL_DELETE_DAY_ENTRY = "DELETE FROM DAYS WHERE DAY_ID=?";


    
}


//******NOTE: SQL DATE IS IN yyyy-MM-dd format