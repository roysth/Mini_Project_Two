package vttp.backend.repository;

import org.springframework.stereotype.Repository;

import vttp.backend.model.Day;
import vttp.backend.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static vttp.backend.Utilities.*;
import static vttp.backend.repository.SQLQueries.*;

@Repository
public class SQLRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Register User in USER
    public void registerUser (User user){
        
        jdbcTemplate.update(SQL_REGISTER_USER, user.getName(), user.getEmail(), user.getPassword(), user.getRole().name());
    }

    //Find user by email in USER
    public Optional<User> findUserByEmail(String email){

        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_USER_BY_EMAIL, email);

        if (!rs.next()) {
            return Optional.empty();
        } 
        User user = createUser(rs);

        return Optional.of(user);
    }

    //Register day in DAYS
    public void registerDay (String day_id, String email, double pnl, Date date) {

        jdbcTemplate.update(SQL_REGISTER_DAY, day_id, email, pnl, date);
    }

    //Find pnl by day_id to update in DAYS
    public double findPnl (String day_id) {

        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_PNL_BY_DAY_ID, day_id);

        rs.next();
        double pnl = rs.getDouble("pnl");
        return pnl;
    }

    //Update the entry's pnl after editing the pnl in DAYS
    public double updatePnl (double originalPnl, double insertedPnl, String day_id) {


        double updatedPnl = originalPnl + insertedPnl;
        //(Shows the number of rows updated)
        int a = jdbcTemplate.update(SQL_UPDATE_PNL, updatedPnl, day_id);

        if (a<0) {
            //Means not updated 
            return 0d;
        }

        return updatedPnl;
    }

    //Get the list of day object to fill the calendar
    public Optional<List<Day>> findListOfDayByEmail (String email, String start, String end) {

        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_DAY_BY_EMAIL, email, start, end);

        if (!rs.next()) {
            return Optional.empty();
        }

        List<Day> listOfDay = new LinkedList<>();
        Day day = createDay(rs);
        listOfDay.add(day);

        while (rs.next()) {
            Day newDay = createDay(rs);
            listOfDay.add(newDay);
        }
        System.out.println(listOfDay.toString());
        return Optional.of(listOfDay);
    }




    
}
