package persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import enums.Order;
import enums.SortBy;
import objects.Friend;


@Component
public class FriendsRepositoryImp implements FriendsRepository{

	private static final Logger logger = LogManager.getLogger(FriendsRepositoryImp.class.getName());
	
	private SessionFactory sessionFactory;
	
	@Inject
	public FriendsRepositoryImp(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public boolean createRelation(Friend first, Friend second) throws HibernateException {
		try{
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			session.saveOrUpdate(first);
			session.saveOrUpdate(second);
			
			transaction.commit();
			session.close();
			
			logger.info("Relations between two users have been created.");
			
			return true;
			
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteRelation(Friend first, Friend second) throws HibernateException {
		try{
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			session.delete(first);
			session.delete(second);
			
			transaction.commit();
			session.close();
			
			logger.info("Relations between two users have been erased.");
			
			return true;
			
		}catch(HibernateException e){
			e.printStackTrace();
			 return false;
		}
	}

	@Override
	public List<Friend> getUserFriends(long userId, int skip, int count, boolean mutual, 
			SortBy sortBy, Order order) throws HibernateException {
		try{
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Friend.class);
			
			dc.add(Restrictions.conjunction(
					Restrictions.eq("userID", userId),
					Restrictions.eq("mutual", mutual)));
			
			DetachedCriteria dcTwo = dc.createCriteria("userFriend");
			
			switch(sortBy){
			case FRIENDSHIP_DATE:{
				
				if(order == Order.ASCENDING) dc.addOrder(org.hibernate.criterion.Order.asc("since"));
				else dc.addOrder(org.hibernate.criterion.Order.desc("since"));
				
			}break;
			case NAME:{
				
				if(order == Order.ASCENDING) dcTwo.addOrder(org.hibernate.criterion.Order.asc("username"));
				else dcTwo.addOrder(org.hibernate.criterion.Order.desc("username"));
				
			}break;
			case POPULARITY:{
				
				if(order == Order.ASCENDING) dcTwo.addOrder(org.hibernate.criterion.Order.asc("popularity"));
				else dcTwo.addOrder(org.hibernate.criterion.Order.desc("popularity"));
				
			}break;
			case RATING:{
				
				if(order == Order.ASCENDING) dcTwo.addOrder(org.hibernate.criterion.Order.asc("rating"));
				else dcTwo.addOrder(org.hibernate.criterion.Order.desc("rating"));
				
			}break;
			case SIGNUP_DATE:{
				
				if(order == Order.ASCENDING) dcTwo.addOrder(org.hibernate.criterion.Order.asc("registrationDate"));
				else dcTwo.addOrder(org.hibernate.criterion.Order.desc("registrationDate"));
				
			}break;
			default:{
				
				if(order == Order.ASCENDING) dc.addOrder(org.hibernate.criterion.Order.asc("since"));
				else dc.addOrder(org.hibernate.criterion.Order.desc("since"));
				
			}break;
			}
			
			@SuppressWarnings("unchecked")
			List<Friend> userList = dc.getExecutableCriteria(session).setFirstResult(skip).setMaxResults(count).list();
			
			session.close();
			
			final StringBuilder log = new StringBuilder("\nFriends of user with id\n [");
			log.append(userId);
			log.append("] have been fetched from db. Wanted count is\n [");
			log.append(count);
			log.append("] but actual is\n [");
			log.append(userList.size());
			log.append("] Mutual is\n [");
			log.append(mutual);
			log.append("] SortBy is\n [");
			log.append(sortBy);
			log.append("] Order is\n [");
			log.append(order);
			
			logger.info(log.toString());
			
			return userList;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Friend> readRelation(long userID, long friendID) throws HibernateException {
		try{
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Friend.class);
			
			Conjunction firstAND = Restrictions.conjunction(
					Restrictions.eq("userID", userID),
					Restrictions.eq("friendID", friendID));
			
			Conjunction secondAND = Restrictions.conjunction(
					Restrictions.eq("friendID", userID),
					Restrictions.eq("userID", friendID));
			
			dc.add(Restrictions.or(firstAND,secondAND));
			
			@SuppressWarnings("unchecked")
			List<Friend> userList = dc.getExecutableCriteria(session).list();
			
			session.close();
			
			logger.info("Friend ["+friendID+"] of user ["+userID+"]  has been successfully fetched from db.");
			
			return userList;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long getCount(long userId) throws HibernateException {
		try{
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Friend.class);
			
			dc.add(Restrictions.conjunction(
					Restrictions.eq("userID", userId),
					Restrictions.eq("mutual", true)));
			dc.setProjection(Projections.rowCount());
			
			Long count = (Long) dc.getExecutableCriteria(session).uniqueResult();
			
			session.close();
			
			logger.info("User with id ["+userId+"] has ["+count+"] friends.");
			
			return count;
		}catch(HibernateException e){
			e.printStackTrace();
			return (long) -1;
		}
	}

	@Override
	public boolean createEntry(Friend friend) throws HibernateException {
		
		try{
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			session.save(friend);
			
			transaction.commit();
			session.close();
			
			logger.info("Friend object has been successfully saved into db.");
			
			return true;
			
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteEntry(Friend friend) throws HibernateException {
		try{
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			session.delete(friend);
			
			transaction.commit();
			session.close();
			
			logger.info("Friend object has been successfully erased from db.");
			
			return true;
			
		}catch(HibernateException e){
			e.printStackTrace();
			 return false;
		}
	}

	@Override
	public List<Friend> readEntry(long userID, long friendID) throws HibernateException {
		try{
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Friend.class);
			
			dc.add(Restrictions.conjunction(
					Restrictions.eq("userID", userID),
					Restrictions.eq("friendID", friendID)));
			
			@SuppressWarnings("unchecked")
			List<Friend> userList = dc.getExecutableCriteria(session).list();
			
			session.close();
			
			logger.info("Friend with id ["+friendID+"] of user whose id is ["+userID+"]  has been successfully fetched from db.");
			
			return userList;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long isRelationExist(long userID, long friendID) throws HibernateException {
		try{
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Friend.class);
			
			dc.add(Restrictions.conjunction(
					Restrictions.eq("userID", userID),
					Restrictions.eq("friendID", friendID)));
			dc.setProjection(Projections.rowCount());
			
			Long count = (Long) dc.getExecutableCriteria(session).uniqueResult();
			
			session.close();
			
			logger.info("Count of relations between user ["+userID+"] and ["+friendID+"] is "+count);
			
			return count;
		}catch(HibernateException e){
			e.printStackTrace();
			return (long) -1;
		}
	}
	
}
