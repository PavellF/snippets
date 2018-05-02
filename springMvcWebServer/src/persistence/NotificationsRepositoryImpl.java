package persistence;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import enums.NotificationType;
import objects.Notification;

@Component
public class NotificationsRepositoryImpl implements NotificationsRepository {

	private SessionFactory sessionFactory;
	
	private static final Logger logger = LogManager.getLogger(NotificationsRepositoryImpl.class.getName());
	
	@Inject
	public NotificationsRepositoryImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
		logger.info("NotificationsRepositoryImpl has been instantiated.");
	}
	
	@Override
	public long saveNotification(Notification notification) {
		
		try{
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			long id = (Long) session.save(notification);
			
			transaction.commit();
			session.close();
			
			logger.info("New notification with id ["+id+"] has been saved into db.");
			
			return id;
		}catch(HibernateException e){
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public Notification getNotification(long id) {
		
		try{
			Session session = sessionFactory.openSession();
			
			Notification notification = (Notification) session.get(Notification.class, id);
			
			session.close();
			
			logger.info("Notification has been successfully fetched from db. String representation is -> "+notification.toString());
			
			return notification;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateNotification(Notification notification) {
		try{
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			session.update(notification);
			
			transaction.commit();
			session.close();
			
			logger.info("UPDATED. String representation of notification object -> "+notification.toString());
			
			return true;
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Notification> getAllNotificationsForUser(long userId, int skip, int count, boolean expired,
			NotificationType[] notificationType,enums.Order order) {
		
		try{
			Session session = sessionFactory.openSession();
			
			DetachedCriteria dc = DetachedCriteria.forClass(Notification.class);
			dc.add(Restrictions.eq("forUser", userId));
			dc.add(Restrictions.eq("expired", expired));
			dc.add(Restrictions.in("type", Arrays.asList(notificationType)));
			
			if(order == enums.Order.ASCENDING){
				dc.addOrder(Order.asc("id"));
			}else{
				dc.addOrder(Order.desc("id"));
			}
			
			@SuppressWarnings("unchecked")
			List<Notification> list = dc.
			getExecutableCriteria(session).
			setFirstResult(skip).
			setMaxResults(count).
			list();
			
			session.close();
			
			StringBuffer str = new StringBuffer("\nWanted count is [");
			str.append(count);
			str.append("]\n actual is [");
			str.append(list.size());
			str.append("]\n notifications for user with id [");
			str.append(userId);
			str.append("]\n Expired is [");
			str.append(expired);
			str.append("]\n Order is [");
			str.append(order);
			str.append("]\n skip is [");
			str.append(skip);
			str.append("]\n With many types.");
			
			logger.info(str.toString());
			
			return list;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Long getCountForUser(long userID) {

		try{
			
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Notification.class);
			
			dc.add(Restrictions.eq("forUser", userID));
			dc.setProjection(Projections.rowCount());
			
			Long count = (Long) dc.getExecutableCriteria(session).uniqueResult();
			
			session.close();
			
			logger.info("User with id ["+userID+"] has ["+count+"] notifications.");
			
			return count;
		}catch(HibernateException he){
			he.printStackTrace();
			return (long) -1;
		}
		
	}

	@Override
	public List<Notification> getAllNotificationsForUser(long userId, int skip, int count, enums.Order order) {

		try{
			Session session = sessionFactory.openSession();
			
			DetachedCriteria dc = DetachedCriteria.forClass(Notification.class);
			dc.add(Restrictions.eq("forUser", userId));
			
			if(order == enums.Order.ASCENDING){
				dc.addOrder(Order.asc("id"));
			}else{
				dc.addOrder(Order.desc("id"));
			}
			
			@SuppressWarnings("unchecked")
			List<Notification> list = dc.getExecutableCriteria(session).setFirstResult(skip).setMaxResults(count).list();
			
			session.close();
			
			StringBuffer str = new StringBuffer("\nWanted count is [");
			str.append(count);
			str.append("]\n actual is [");
			str.append(list.size());
			str.append("]\n notifications for user with id [");
			str.append(userId);
			str.append("]\n Order is [");
			str.append(order);
			str.append("]\n skip is [");
			str.append(skip);
			str.append("]\n With all types.");
			
			logger.info(str.toString());
			
			return list;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Notification> getSelectedNotifications(List<Long> ids) throws HibernateException {
		
		try{
			Session session = sessionFactory.openSession();
			
			DetachedCriteria dc = DetachedCriteria.forClass(Notification.class);
			dc.add(Restrictions.in("id", ids));
			
			@SuppressWarnings("unchecked")
			List<Notification> list = dc.getExecutableCriteria(session).list();
			
			session.close();
			
			StringBuffer str = new StringBuffer("\nWanted count is [");
			str.append(ids.size());
			str.append("]\n actual is [");
			str.append(list.size());
			str.append("]\n notifications with these ids.");
			
			logger.info(str.toString());
			
			return list;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateSelectedNotifications(List<Notification> list) throws HibernateException {

		try{
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			list.forEach((val) -> {session.update(val);});
			
			transaction.commit();
			session.close();
			
			logger.info(list.size()+" notifications just were updated.");
			
			return true;
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}

}
