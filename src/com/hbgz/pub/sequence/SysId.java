package com.hbgz.pub.sequence;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hbgz.model.SysSequence;

/**
 * 
 * <p>
 * <h2>SysId工具类用于取得主键序列号。</h2>
 * </p>
 * 
 * <p>
 * SysId工具类根据系统配置工具类SysConfig.getSqlSeq()函数返回的SQL取得Oracle序列
 * 号的值，并在本地缓存N个序列号，N的值是由SysConfig.getSeqCache()函数返回值决定的。
 * 在当前系统框架内，所有表使用同一个序列号（即：全局序列号）。这样做的好处是，可以大大的 减少数据库访问的次数，取N个序列号的值只需访问数据库一次。
 * </p>
 * 
 * <p>
 * 在创建Oracle序列号时，需要指定序列号的步进值（即：每次读取序列号时，增加N个值），SQL 类似于：<br>
 * 
 * <pre>
 * create sequence mofs_seq increment by 100 cache 20;
 * </pre>
 * 
 * 其中：
 * 
 * <pre>
 * increment by 100
 * </pre>
 * 
 * 表示步进为100，即SysConfig.getSeqCache() 函数的返回值应该为100。
 * </p>
 * 
 * <p>
 * SysId工具类还可以根据Oracle序列号的名字取得序列号的值，但此种方式不支持序列号缓存，
 * 每次去序列号时，都需要访问数据库，效率较低。以此种方式取序列号时，序列号的步进值应该 为1。
 * </p>
 * 
 */
@Component
public class SysId {
	@Autowired
	private SysSeq sysSeq;

	private static Log log = LogFactory.getLog(SysId.class);

	/** 序列号名称 */
	public static String SeqNameID = "SEQ_ID";

	// 图片序列号
	public static String seqImageId = "SEQ_IMAGE";

	// 日志序列号
	public static String seqLogId = "SEQ_LOG_ID";

	// 支持多个序列号，将序列号名称和取序列号的SQL放在该影射中
	// Map(seqName, Map(regionId, PrimaryKey))
	private static Map seqMap = new HashMap();

	/** 通过主键序列号定义表取得主键:SEQ_ID */
	public Long getId() {
		try {

			return sysSeq.getSequenceItzc(SeqNameID);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 通过主键序列号定义表取得主键:SEQ_ID */
	public Long getId(String seqName) {
		try {
			if (seqName == null || "".equals(seqName)) {
				return getSeq(SeqNameID, null);
			} else {
				return getSeq(seqName, null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过主键序列号定义表取得主键
	 * 
	 * @throws Exception
	 */
	private Long getSeq(String seqName, Long regionId) throws Exception {
		String seq = seqName;
		if (seqName == null || seqName.length() <= 0) {
			seq = SeqNameID;
		}

		// 取得缓存的序列号
		PrimaryKey pk = null;
		Object obj = seqMap.get(seq);
		if (obj == null) {
			pk = new PrimaryKey();
			seqMap.put(seq, pk);
		} else {
			pk = (PrimaryKey) obj;
		}

		// 取得缓存的序列号值
		Long id = nextSeq(seq, pk);

		if (log.isDebugEnabled()) {
			log.debug("取得序列号(" + seqName + ")的值：" + id);
		}

		return id;
	}

	// 取得缓存的序列号值
	private Long nextSeq(String seqName, PrimaryKey pk) throws Exception {
		synchronized (pk) {
			Long id = null;
			id = pk.next();
			if (id == null) {
				// 没有取得序列号
				try {
					SysSequence po = sysSeq.getSequence(seqName);
					long start = po.getStartValue();
					long end = po.getEndValue();
					pk.setId(start);
					pk.setMaxId(end);
					id = pk.next();
				} catch (Throwable t) {
					throw new Exception("取得序列号失败: " + seqName, t);
				}
			}
			return id;
		}
	}

	// 缓存序列号的内部类
	private static class PrimaryKey {
		public PrimaryKey() {

		}

		private long id = 0L;
		private long maxId = -1L;

		public void setMaxId(long maxId) {
			this.maxId = maxId;
		}

		public void setId(long id) {
			this.id = id;
		}

		public Long next() {
			if (id <= maxId) {
				return new Long(id++);
			}
			return null;
		}
	}
}
