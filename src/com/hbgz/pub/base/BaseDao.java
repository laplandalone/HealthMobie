package com.hbgz.pub.base;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 所有DAO类的基类
 * </p>
 * 
 * <p>
 * BaseDao抽象基类实现对model类增删查改的基本功能，BaseDao抽象基类有HibernateTemplate属性，
 * 继承子类可通过该属性完成其它对model类复杂操作，命名需要以addXXX,updateXXX,deleteXXX,findXXX开头。
 * </p>
 * 
 * <p>
 * 继续BaseDao抽象基类的子类需要每个DAO类对应一个model类,通过注解类型@Entity完成映射。
 * </p>
 * 
 * <p>
 * BaseDao抽象基类，子类通过注解方式实例化，禁止通过new&nbsp;XxxDao()获得DAO实例。
 * </p>
 * 
 *<P>
 * 涉及事务管理，BaseDao抽象基类及继承子类只能通过注解方式注入到服务层使用，不可在其它层使用。
 *</P>
 * 
 */
@Repository
public abstract class BaseDao 
{
	private HibernateTemplate hibernateTemplate;

	public Log log = LogFactory.getLog(BaseDao.class);

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) 
	{
		this.hibernateTemplate = hibernateTemplate;
	}

	public HibernateTemplate getHibernateTemplate() 
	{
		return hibernateTemplate;
	}

	/** 保存PO对象到数据库 */
	public void save(Object entity) 
	{
		hibernateTemplate.clear(); // added by yangjingwen for exception
		System.out.println(hibernateTemplate.save(entity));
	}

	/** 删除数据库PO对象 */
	public void delete(Object entity) 
	{
		hibernateTemplate.delete(entity);
	}

	/** 修改数据库PO对象 */
	public void update(Object entity) 
	{
		hibernateTemplate.update(entity);
	}

	public List find(String queryString) 
	{
		return hibernateTemplate.find(queryString);
	}

	/** 根据属性查询对象 查询状态为可用的对象 */
	public List findByProperty(String modelName, String propertyName, Object value) 
	{
		log.debug("modelName: " + modelName + "propertyName" + propertyName + ", value: " + value);
		try 
		{
			String queryString = "from " + modelName + " as model where model.state = '00A' and model." + propertyName + "= ?";
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, value);
			List list = queryObject.list();
			session.close();
			return list;
		} 
		catch (RuntimeException re) 
		{
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/** 根据属性查询对象 可用和历史状态的对象都会被查询出来 */
	public List findListByProperty(String modelName, String propertyName, Object value) 
	{
		log.debug("modelName: " + modelName + "propertyName" + propertyName + ", value: " + value);
		try 
		{
			String queryString = "from " + modelName + " as model where model." + propertyName + "= ?";
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, value);
			List list = queryObject.list();
			session.close();
			return list;
		}
		catch (RuntimeException re) 
		{
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/** 根据多项属性查询对象　后期完善 */
	public List findByPropertys(String modelName, String propertyName, Object value) 
	{

		log.debug("modelName: " + modelName + "propertyName" + propertyName + ", value: " + value);
		try 
		{
			String queryString = "from " + modelName + " as model where model." + propertyName + "= ?";
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, value);
			List list = queryObject.list();
			session.close();
			return list;
		} 
		catch (RuntimeException re) 
		{
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public boolean delete(String modelName, String propertyName, String value)
	{
		String queryString = "delete " + modelName + " as model where model." + propertyName + "= ?";
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter(0, value);
		queryObject.executeUpdate();
		session.close();
		return true;
	}
	
	public List find(String query, Object[] list) 
	{
		return this.getHibernateTemplate().find(query, list);
	}
}
