package com.hbgz.pub.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.hbgz.pub.base.BaseDao;
import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.qry.QryCenter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
	private static Log log = LogFactory.getLog(SysCacheDao.class);
	
	@Autowired
	private QryCenter itzcQryCenter;
	
	@Cacheable(cacheName = "HospitalConfig")
	public List getHospitalConfig() throws QryException
	{
		StringBuffer sql = new StringBuffer();
		ArrayList lstParam = new ArrayList();
		sql.append("select t.* from hospital_config_t t where state='00A' order by config_id ");
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}
	
	@Cacheable(cacheName = "AuthCode")
    public Map getAuthCode(String accNbr)
    {
    	Map map = new HashMap();
    	String authCode =new Random().nextInt(999999)+"";
    	map.put(accNbr, authCode);
    	log.error(map);
    	return map;
    }

	@Cacheable(cacheName = "HospitalConfig")
	public List qryTeamList(String hospitalId) throws QryException 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from team_t where state = '00A' and expert_flag = '1' and hospital_id = ? ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(hospitalId);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}

	@Cacheable(cacheName = "HospitalConfig")
	public List qryDoctorList(String hospitalId) throws QryException 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from doctor_t where state = '00A' and hospital_id = ? ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(hospitalId);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}
	
	@TriggersRemove(cacheName="HospitalConfig",removeAll=true) 
	public void delConfigCache(){}
}
