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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import enums.Order;
import enums.OriginType;
import enums.SortBy;
import objects.Message;

@Component
public class MessagesRepositoryImpl implements MessagesRepository {

	private static final Logger logger = LogManager.getLogger(MessagesRepositoryImpl.class.getName());
	
	private SessionFactory sessionFactory;
	
	@Inject
	public MessagesRepositoryImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<Message> getAllMessagesForUser(long userID, Archived archived, SortBy sort, Order order, int count,
			int skip) {
		try{
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Message.class);
			
			switch(archived){
			case DELETED: dc.add(Restrictions.conjunction(
					Restrictions.eq("author", userID),
					Restrictions.eq("archived", true)));
				break;
			case IGNORE: dc.add(Restrictions.eq("author", userID));
				break;
			case NOT_DELETED: dc.add(Restrictions.conjunction(
					Restrictions.eq("author", userID),
					Restrictions.eq("archived", false)));
				break;
			}
			
			switch(sort){
			case DATE: {
				
				if(order == Order.ASCENDING) dc.addOrder(org.hibernate.criterion.Order.asc("id"));
				else dc.addOrder(org.hibernate.criterion.Order.asc("id"));
					
			}break;
			case RATING: {
				
				if(order == Order.ASCENDING) dc.addOrder(org.hibernate.criterion.Order.asc("rating"));
				else dc.addOrder(org.hibernate.criterion.Order.asc("rating"));
				
			}break;
			default: {
				
				if(order == Order.ASCENDING) dc.addOrder(org.hibernate.criterion.Order.asc("rating"));
				else dc.addOrder(org.hibernate.criterion.Order.asc("rating"));
				
			}break;
			}
			
			@SuppressWarnings("unchecked")
			List<Message> messageList = dc.getExecutableCriteria(session).setFirstResult(skip).setMaxResults(count).list();
			
			session.close();
			
			final StringBuilder log = new StringBuilder("Expected count of messages from user with id [");
			log.append(userID);
			log.append("] is [");
			log.append(count);
			log.append("] but actual is [");
			log.append(messageList.size());
			log.append("] was skipped [");
			log.append(skip);
			log.append("] archived value is [");
			log.append(archived);
			log.append("] SortBy value is [");
			log.append(sort);
			log.append("] Order value is [");
			log.append(order);
			
			logger.info(log.toString());
			
			return messageList;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Message> getAllMessages(OriginType originType, long originID, Archived archived, SortBy sort,
			Order order, int count, int skip) {
		try{
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Message.class);
			
			switch(archived){
			case DELETED:dc.add(Restrictions.conjunction(
					Restrictions.eq("originType", originType),
					Restrictions.eq("originId", originID),
					Restrictions.eq("archived", true),
					Restrictions.isNull("replied")));
				break;
			case IGNORE:dc.add(Restrictions.conjunction(
					Restrictions.eq("originType", originType),
					Restrictions.eq("originId", originID),
					Restrictions.isNull("replied")));
				break;
			case NOT_DELETED:dc.add(Restrictions.conjunction(
					Restrictions.eq("originType", originType),
					Restrictions.eq("originId", originID),
					Restrictions.eq("archived", false),
					Restrictions.isNull("replied")));
				break;
			}
			
			switch(sort){
			case DATE: {
				
				if(order == Order.ASCENDING) dc.addOrder(org.hibernate.criterion.Order.asc("id"));
				else dc.addOrder(org.hibernate.criterion.Order.desc("id"));
					
			}break;
			case RATING: {
				
				if(order == Order.ASCENDING) dc.addOrder(org.hibernate.criterion.Order.asc("rating"));
				else dc.addOrder(org.hibernate.criterion.Order.desc("rating"));
				
			}break;
			default: {
				
				if(order == Order.ASCENDING) dc.addOrder(org.hibernate.criterion.Order.asc("rating"));
				else dc.addOrder(org.hibernate.criterion.Order.desc("rating"));
				
			}break;
			}
			
			@SuppressWarnings("unchecked")
			List<Message> messageList = dc.getExecutableCriteria(session).setFirstResult(skip).setMaxResults(count).list();
			
			session.close();
			
			final StringBuilder log = new StringBuilder("Expected amount of messages from origin with type [");
			log.append(originType);
			log.append("] and id [");
			log.append(originID);
			log.append("] is [");
			log.append(count);
			log.append("] but actual is [");
			log.append(messageList.size());
			log.append("] was skipped [");
			log.append(skip);
			log.append("] archived value is [");
			log.append(archived);
			log.append("] SortBy value is [");
			log.append(sort);
			log.append("] Order value is [");
			log.append(order);
			
			logger.info(log.toString());
			
			return messageList;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long create(Message message) {
		try{
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			Long id = (Long) session.save(message);
			
			transaction.commit();
			session.close();
			
			logger.info("Message object has been successfully saved into db. Generated ID is returning..");
			
			return id;
			
		}catch(HibernateException e){
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public boolean update(Message message) {
		try{
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			session.update(message);
			
			transaction.commit();
			session.close();
			
			logger.info("A message object was UPDATED.");
			
			return true;
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Message message) {
		try{
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			session.delete(message);
			
			transaction.commit();
			session.close();
			
			logger.info("A message object was DELETED.");
			
			return true;
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Long getCount(long userID, Archived archived) {
		try{
			
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Message.class);
			
			switch(archived){
			case DELETED: dc.add(Restrictions.conjunction(
					Restrictions.eq("author", userID),
					Restrictions.eq("archived", true)));
				break;
			case IGNORE: dc.add(Restrictions.eq("author", userID));
				break;
			case NOT_DELETED: dc.add(Restrictions.conjunction(
					Restrictions.eq("author", userID),
					Restrictions.eq("archived", false)));
				break;
			}
			
			dc.setProjection(Projections.rowCount());
			
			Long count = (Long) dc.getExecutableCriteria(session).uniqueResult();
			
			session.close();
			
			final StringBuffer log = new StringBuffer("User with id [");
			log.append(userID);
			log.append("] has [");
			log.append(count);
			log.append("] messages.");
			log.append("] With restriction value archived [");
			log.append(archived);
			
			logger.info(log.toString());
			
			return count;
		}catch(HibernateException he){
			he.printStackTrace();
			return (long) -1;
		}
	}

	@Override
	public Long getCount(OriginType originType, long originID, Archived archived) {
		try{
			
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Message.class);
			
			switch(archived){
			case DELETED:dc.add(Restrictions.conjunction(
					Restrictions.eq("originType", originType),
					Restrictions.eq("originId", originID),
					Restrictions.eq("archived", true)));
				break;
			case IGNORE:dc.add(Restrictions.conjunction(
					Restrictions.eq("originType", originType),
					Restrictions.eq("originId", originID)));
				break;
			case NOT_DELETED:dc.add(Restrictions.conjunction(
					Restrictions.eq("originType", originType),
					Restrictions.eq("originId", originID),
					Restrictions.eq("archived", true)));
				break;
			}
			
			dc.setProjection(Projections.rowCount());
			
			Long count = (Long) dc.getExecutableCriteria(session).uniqueResult();
			
			session.close();
			
			final StringBuffer log = new StringBuffer("In origin which type is [");
			log.append(originType);
			log.append("] and id [");
			log.append(originID);
			log.append("] contains  [");
			log.append(count);
			log.append("] messages.");
			log.append("] With restriction value archived [");
			log.append(archived);
			
			logger.info(log.toString());
			
			return count;
		}catch(HibernateException he){
			he.printStackTrace();
			return (long) -1;
		}
	}

	@Override
	public Message read(long id) throws HibernateException {
		try{
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			Message m = session.get(Message.class, id);
			
			transaction.commit();
			session.close();
			
			logger.info("A message object was FETCHED.");
			
			return m;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

}
