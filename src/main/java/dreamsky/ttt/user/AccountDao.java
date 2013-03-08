package dreamsky.ttt.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao {
	private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);
	private JdbcTemplate jdbcTpl;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTpl = new JdbcTemplate(dataSource);
	}
	
	private final static RowMapper<Account> rowMapper = new RowMapper<Account>() {
		public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
			Account account = new Account();
			account.setUserId(rs.getLong("user_id"));
			account.setAvailableBalance(rs.getLong("available_balance"));
			account.setTotalBalance(rs.getLong("total_balance"));
			account.setVersion(rs.getInt("version"));
			return account;
		}
	};

	public void save(Account account) {
		jdbcTpl.update("INSERT INTO account (user_id, total_balance, available_balance) VALUES (?,?,?)", 
				account.getUserId(), account.getTotalBalance(), account.getAvailableBalance());
	}
	
	public void update(Account account) {
		if (jdbcTpl.update("UPDATE account SET total_balance=?, available_balance=?, version=version+1 WHERE user_id=? and version=?",
				account.getTotalBalance(), account.getAvailableBalance(), account.getUserId(), account.getVersion()) < 1) {
			String msg = "concurrent modification for entity account " + account.getUserId();
			logger.warn(msg);
			throw new OptimisticLockingFailureException(msg);
		}
	}

	public Account get(Long id) {
		return jdbcTpl.queryForObject("SELECT * FROM account WHERE user_id=?", rowMapper, id);
	}
}
