package vttp.backend.model;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Day {

    private String day_id; //To be inserted into Mongo. Generated if its empty.
    private double pnl; //This will be the latest pnl, which is always updated everytime a journal entry is entered 
    private Journal[] journalList;
    private String email;
    private Date date;

    
}

// SQL DAY TABLE:
// day_id varchar(8),
// pnl decimal(10,2),

// primary key(day_id),
// email varchar(128) not null,

// constraint fk_email
//     foreign key(email)
//     references users(email)