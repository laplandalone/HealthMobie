package com.hbgz.pub.cache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hbgz.pub.base.BaseDao;
import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.qry.QryCenter;

/**
 *<p>
 * 1、利用ehcache,将数据层查询结果集缓存。<br>
 *<p>
 * 2、SysCacheDao缓存数据管理器，使用@Cacheable将集合缓存，SysCacheDao提供源表数据。<br>
 *<p>
 * 3、切忌@Cacheable只能在SysCacheDao类及ServiceCahce类(服务缓存)类使用，便于缓存数据集中配制管理。<br>
 * 
 * @author RHQ
 */
@Repository
public class SysCacheDao extends BaseDao
{
	@Autowired
	private QryCenter itzcQryCenter;
	
	@Cacheable(cacheName = "HospitalConfig")
	public List getHospitalConfig() throws QryException
	{
		StringBuffer sql = new StringBuffer();
		ArrayList lstParam = new ArrayList();
		sql.append("select t.* from hospital_config_t t where state='00A' ");
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}

}
