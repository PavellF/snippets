package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import objects.FullMathExpression;

@Component
public class MathRepositoryImpl implements MathRepository {

	private final static String TABLE_NAME = "EXPRESSIONS";
	private final static String SQL_INSERT_EXPRESSION = "INSERT INTO "+TABLE_NAME+
			" (expression,difficulty,answer) VALUES(?,?,?);";
	private final static String SQL_SELECT_EXPRESSION_BY_ID = "SELECT * FROM "+TABLE_NAME+" WHERE id=?;";
	private final static String SQL_DELETE_EXPRESSION = "DELETE FROM "+TABLE_NAME+" WHERE id=?;";
	private final static String SQL_FIND_ALL_EXPRESSIONS ="SELECT * FROM "+TABLE_NAME+";";
	private final static String SQL_FIND_ALL_WITH_THIS_COMPLEXITY = "SELECT * FROM "+TABLE_NAME+" WHERE difficulty=?;";
	private final static String SQL_FIND_ALL_WITHIN_THIS_ID_RANGE = "SELECT * FROM "+TABLE_NAME+" WHERE id>? AND id<?;";
	/*
	 CREATE TABLE expressions(
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	expression MEDIUMTEXT NOT NULL,
	difficulty MEDIUMINT NULL,
	answer MEDIUMTEXT NULL);
	 */
	@Autowired
	private DataSource dataSource;
	
	@Override
	public long save(FullMathExpression expression) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			connection = this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_INSERT_EXPRESSION, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, expression.getStatement());
			preparedStatement.setInt(2, expression.getComplex());
			preparedStatement.setString(3, expression.getAnswer());
			preparedStatement.execute();
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) return resultSet.getLong(1);
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement, resultSet);
		}
		return -1;
	}
	
	@Override
	public FullMathExpression findById(long id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try{
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_SELECT_EXPRESSION_BY_ID);
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			FullMathExpression fullMathExpression = null;
			if(resultSet.next()){
				fullMathExpression = new FullMathExpression();
				fullMathExpression.setStatement(resultSet.getString("expression"));
				fullMathExpression.setId(resultSet.getLong("id"));
				fullMathExpression.setAnswer(resultSet.getString("answer"));
				fullMathExpression.setComplex(resultSet.getInt("difficulty"));
			}
			return fullMathExpression;
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public List<FullMathExpression> findAllWithThisComplexity(int complexity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_FIND_ALL_WITH_THIS_COMPLEXITY);
			preparedStatement.setInt(1, complexity);
			resultSet = preparedStatement.executeQuery();
			List<FullMathExpression> result = new ArrayList<>(100);
			while(resultSet.next()){ 
				FullMathExpression fullMathExpression = new FullMathExpression();
				fullMathExpression.setStatement(resultSet.getString("expression"));
				fullMathExpression.setId(resultSet.getLong("id"));
				fullMathExpression.setAnswer(resultSet.getString("answer"));
				fullMathExpression.setComplex(resultSet.getInt("difficulty"));
				result.add(fullMathExpression);
			}
			return result;
		}catch(SQLException sqle){
			System.err.println("SQLException");
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public List<FullMathExpression> findAllWhitinThisIdRange(long start, long end) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_FIND_ALL_WITHIN_THIS_ID_RANGE);
			preparedStatement.setLong(1, start);
			preparedStatement.setLong(2, end);
			resultSet = preparedStatement.executeQuery();
			List<FullMathExpression> result = new ArrayList<>(100);
			while(resultSet.next()){ 
				FullMathExpression fullMathExpression = new FullMathExpression();
				fullMathExpression.setStatement(resultSet.getString("expression"));
				fullMathExpression.setId(resultSet.getLong("id"));
				fullMathExpression.setAnswer(resultSet.getString("answer"));
				fullMathExpression.setComplex(resultSet.getInt("difficulty"));
				result.add(fullMathExpression);
			}
			return result;
		}catch(SQLException sqle){
			System.err.println("SQLException");
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public boolean delete(long id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_DELETE_EXPRESSION);
			preparedStatement.setLong(1, id);
			preparedStatement.execute();
			return true;
		}catch(SQLException sqle){
			System.err.println("SQLException");
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement,null);
		}
		return false;
	}

	@Override
	public List<FullMathExpression> fetchAll() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_FIND_ALL_EXPRESSIONS);
			resultSet = preparedStatement.executeQuery();
			List<FullMathExpression> result = new ArrayList<>(100);
			while(resultSet.next()){ 
				FullMathExpression fullMathExpression = new FullMathExpression();
				fullMathExpression.setStatement(resultSet.getString("expression"));
				fullMathExpression.setId(resultSet.getLong("id"));
				fullMathExpression.setAnswer(resultSet.getString("answer"));
				fullMathExpression.setComplex(resultSet.getInt("difficulty"));
				result.add(fullMathExpression);
			}
			return result;
		}catch(SQLException sqle){
			System.err.println("SQLException");
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement, resultSet);
		}
		return null;
	}
	
	private void catchExceptions(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
		
		try{
			if(resultSet != null) resultSet.close(); 
			
			if(preparedStatement != null) preparedStatement.close();
			
			if(connection != null) connection.close();
			
		}catch(SQLException sqle){
			System.err.println("SQLException");
			sqle.printStackTrace();
		}
}

}
