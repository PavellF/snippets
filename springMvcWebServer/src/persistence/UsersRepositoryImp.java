
package persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import objects.User;
import services.UserServiceImpl;

@Repository
public class UsersRepositoryImp implements UsersRepository {

	private SessionFactory sessionFactory;
	
	private static final Logger logger = LogManager.getLogger(UsersRepositoryImp.class.getName());
	
	@Inject
	public UsersRepositoryImp(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public long persistUser(User user) {

		try{
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			logger.info("Trying to persist new User...");
			
			long generatedId = (Long) session.save(user);
			
			transaction.commit();
			session.close();
			
			logger.info("Returned id is ["+generatedId+"] User has been successfully saved into db if id is positive value.");
			
			return generatedId;
		}catch(HibernateException he){
			he.printStackTrace();
			return -1;
		}
	}

	@Override
	public User findById(long id) {
		try{
			
			Session session = sessionFactory.openSession();
			
			logger.info("Trying to fetch user with id "+id);
			
			User user = session.get(User.class, id);
			
			session.close();
			
			return user;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
			}
	}

	@Override
	public User findByUsername(String username) {
		try{
			
			Session session = sessionFactory.openSession();
			
			DetachedCriteria dc = DetachedCriteria.forClass(User.class);
			
			dc.add(Restrictions.eq("username", username));
			
			logger.info("Trying to fetch user with username "+username);
			
			User user = (User) dc.getExecutableCriteria(session).uniqueResult();
			
			session.close();
			
			return user;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> getAll() {
		try{
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(User.class);
			@SuppressWarnings("unchecked")
			List<User> userList = dc.getExecutableCriteria(session).list();
			session.close();
			return userList;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean delete(Long id) {
		try{
			Session session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			User willBeDeleted = new User();
			willBeDeleted.setId(id);
			
			session.delete(willBeDeleted);
			
			transaction.commit();
			session.close();
			
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(User user) {
		try{
			Session session = this.sessionFactory.openSession();
			session.update(user);
			session.beginTransaction().commit();
			session.close();
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}
	}
/*
	@Override
	public boolean doesUserExist(String username) {
		try{
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(User.class);
			dc.add(Restrictions.eq("username", username));
			
			if(dc.getExecutableCriteria(session).uniqueResult() != null){
				session.close();
				logger.info("User with username "+username+" exist.");
				return true;
			}else{
				session.close();
				logger.info("User with username "+username+" does not exist.");
				return false;
			}
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean doesEmailExist(String email) {
		try{
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(User.class);
			dc.add(Restrictions.eq("email", email));
			
			if(dc.getExecutableCriteria(session).uniqueResult() != null){
				session.close();
				logger.info("Email "+email+" exist.");
				return true;
			}else{
				session.close();
				logger.info("Email "+email+" does not exist.");
				return false;
			}
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}
*/
	@Override
	public User findByEmail(String email) {
		try{
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(User.class);
			dc.add(Restrictions.eq("email", email));
			logger.info("Trying to fetch user with email "+email);
			User user = (User) dc.getExecutableCriteria(session).uniqueResult();
			session.close();
			return user;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> getAllWithId(List<Long> ids) {
		try{
			Session session = sessionFactory.openSession();
			
			DetachedCriteria dc = DetachedCriteria.forClass(User.class);
			dc.add(Restrictions.in("id",ids));
			
			@SuppressWarnings("unchecked")
			List<User> userList = dc.getExecutableCriteria(session).list();
			
			session.close();
			return userList;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

}
