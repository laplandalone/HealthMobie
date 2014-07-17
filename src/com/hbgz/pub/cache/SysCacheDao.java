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
 * 1������ehcache,�����ݲ��ѯ��������档<br>
 *<p>
 * 2��SysCacheDao�������ݹ�������ʹ��@Cacheable�����ϻ��棬SysCacheDao�ṩԴ�����ݡ�<br>
 *<p>
 * 3���м�@Cacheableֻ����SysCacheDao�༰ServiceCahce��(���񻺴�)��ʹ�ã����ڻ������ݼ������ƹ���<br>
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
