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
 * ����DAO��Ļ���
 * </p>
 * 
 * <p>
 * BaseDao�������ʵ�ֶ�model����ɾ��ĵĻ������ܣ�BaseDao���������HibernateTemplate���ԣ�
 * �̳������ͨ�����������������model�ิ�Ӳ�����������Ҫ��addXXX,updateXXX,deleteXXX,findXXX��ͷ��
 * </p>
 * 
 * <p>
 * ����BaseDao��������������Ҫÿ��DAO���Ӧһ��model��,ͨ��ע������@Entity���ӳ�䡣
 * </p>
 * 
 * <p>
 * BaseDao������࣬����ͨ��ע�ⷽʽʵ��������ֹͨ��new&nbsp;XxxDao()���DAOʵ����
 * </p>
 * 
 *<P>
 * �漰�������BaseDao������༰�̳�����ֻ��ͨ��ע�ⷽʽע�뵽�����ʹ�ã�������������ʹ�á�
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

	/** ����PO�������ݿ� */
	public void save(Object entity) 
	{
		hibernateTemplate.clear(); // added by yangjingwen for exception
		System.out.println(hibernateTemplate.save(entity));
	}

	/** ɾ�����ݿ�PO���� */
	public void delete(Object entity) 
	{
		hibernateTemplate.delete(entity);
	}

	/** �޸����ݿ�PO���� */
	public void update(Object entity) 
	{
		hibernateTemplate.update(entity);
	}

	public List find(String queryString) 
	{
		return hibernateTemplate.find(queryString);
	}

	/** �������Բ�ѯ���� ��ѯ״̬Ϊ���õĶ��� */
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

	/** �������Բ�ѯ���� ���ú���ʷ״̬�Ķ��󶼻ᱻ��ѯ���� */
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

	/** ���ݶ������Բ�ѯ���󡡺������� */
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
