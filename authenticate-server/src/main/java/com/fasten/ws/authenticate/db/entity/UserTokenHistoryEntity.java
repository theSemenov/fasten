package com.fasten.ws.authenticate.db.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="user_token_history_tbl")
public class UserTokenHistoryEntity extends TokenEntity {
	private String action;
}
