package testutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.google.gson.Gson;

import enums.Roles;
import objects.Friend;
import objects.Message;
import objects.User;
import persistence.FriendsRepository;
import persistence.FriendsRepositoryImp;
import persistence.UsersRepository;
import persistence.UsersRepositoryImp;
import persistenceConfig.HibernateConfig;
import util.Properties;

public class AddFriends {
	
	static SessionFactory sessionFactory = getSF();
	static FriendsRepository friendsRepository = new FriendsRepositoryImp(sessionFactory);
	static UsersRepository usersRepository = new UsersRepositoryImp(sessionFactory);
	
	public static void main(String[] args) {
		/*
		try{
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Friend.class);
			
			DetachedCriteria dcTwo = dc.createCriteria("friend_id");
			
			dc.add(Restrictions.eq("user_id",(long)1));
						
			dcTwo.addOrder(Order.asc("email"));
			
			@SuppressWarnings("unchecked")
			List<Friend> userList = dc.getExecutableCriteria(session).setMaxResults(10).list();
			session.close();
			
			System.err.println(userList.get(0).getFriend().getEmail());
			
		}catch(HibernateException e){
			e.printStackTrace();
		}
		*/
		/*
		for(int i=0;i<56;i++){
		
		final User firstFriend = persistNewUser();
		
		final Friend first = new Friend();
		first.setUserID((long) 1);
		first.setFriend(firstFriend);
		first.setMutual(false);
		
		friendsRepository.createEntry(first);
		
		}
		*/
		
		try{
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Message.class);
			
			dc.add(Restrictions.eq("id",(long)1));
						
			
			@SuppressWarnings("unchecked")
			List<Message> userList = dc.getExecutableCriteria(session).setMaxResults(1).list();
			session.close();
			
			System.err.println(userList.size());
			System.err.println(userList.get(0).getReplies().size());
			System.err.println(new Gson().toJson(userList));
			
		}catch(HibernateException e){
			e.printStackTrace();
		}
		
		
		
	}
	
	private static User persistNewUser(){
		
		User user = new User();
		user.setBlocked(false);
		user.setEmail("T@Tester.COM"+(Math.floor(Math.random()*10E6)));
		user.setPassword("passw"+Math.floor(Math.random()*10E6));
		user.setRegistrationDate(new Date());
		user.setUsername("tester"+Math.floor(Math.random()*1E6));
		user.setRole(Roles.USER);
		user.setUserPicURL(Properties.get().getString("DEFAULT_USER_PICTURE_URL"));
		
		long id = usersRepository.persistUser(user);
		
		user.setId(id);
		
		return user;
	}
	
	private static SessionFactory getSF(){
		Properties.instance("WebContent/META-INF/prop.properties");
		return new HibernateConfig().createSessionFactory();
	}

}
