package vttp.backend.repository;

public class SQLQueries {

    //Find user by email in USER
    public static final String SQL_FIND_USER_BY_EMAIL = "select * from users where email=?";

    //Register user in USER
    public static final String SQL_REGISTER_USER = "insert into users (name, email, password, role) values (?,?,?,?)";

    //Register day in DAYS
    public static final String SQL_REGISTER_DAY = "insert into days (day_id, email, pnl, day) values (?,?,?,?)";

    //Find pnl via day_id in DAYS
    public static final String SQL_FIND_PNL_BY_DAY_ID = "select pnl from days where day_id=?";

    //Update the entry after editing the pnl in DAYS
    public static final String SQL_UPDATE_PNL = "update days set pnl=? where day_id=?";

    //Given the email (taken from jwt) and a period of time between XX date and YY date (to fill the calender), 
    //get the Day object (day_id, pnl, date) from DAYS to be formed as a List<Day> later
    public static final String SQL_GET_DAY_BY_EMAIL = "select day_id, pnl, day from days where email=? and day between ? and ?";

    //Get the day_id using email and day in DAYS.
    //If day_id is present, send it to teh frontend and tie it to the journal entry. Else, create a new one when the journal is sent over 
    public static final String SQL_GET_DAY_ID_BY_EMAIL_AND_DAY = "select day_id from days where email=? and day=?";
 
    //Upate the pnl when a Journal entry is DELETED in DAYS
    public static final String SQL_UPDATE_PNL_WHEN_JOURNAL_IS_DELETED = "update days set pnl = pnl-? where day_id=?";

    //Delete Day entry from Days
    public static final String SQL_DELETE_DAY_ENTRY = "delete from days where day_id=?";


    
}


//******NOTE: SQL DATE IS IN yyyy-MM-dd format