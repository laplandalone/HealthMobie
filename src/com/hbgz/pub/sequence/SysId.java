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
 * <h2>SysId����������ȡ���������кš�</h2>
 * </p>
 * 
 * <p>
 * SysId���������ϵͳ���ù�����SysConfig.getSqlSeq()�������ص�SQLȡ��Oracle����
 * �ŵ�ֵ�����ڱ��ػ���N�����кţ�N��ֵ����SysConfig.getSeqCache()��������ֵ�����ġ�
 * �ڵ�ǰϵͳ����ڣ����б�ʹ��ͬһ�����кţ�����ȫ�����кţ����������ĺô��ǣ����Դ��� �������ݿ���ʵĴ�����ȡN�����кŵ�ֵֻ��������ݿ�һ�Ρ�
 * </p>
 * 
 * <p>
 * �ڴ���Oracle���к�ʱ����Ҫָ�����кŵĲ���ֵ������ÿ�ζ�ȡ���к�ʱ������N��ֵ����SQL �����ڣ�<br>
 * 
 * <pre>
 * create sequence mofs_seq increment by 100 cache 20;
 * </pre>
 * 
 * ���У�
 * 
 * <pre>
 * increment by 100
 * </pre>
 * 
 * ��ʾ����Ϊ100����SysConfig.getSeqCache() �����ķ���ֵӦ��Ϊ100��
 * </p>
 * 
 * <p>
 * SysId�����໹���Ը���Oracle���кŵ�����ȡ�����кŵ�ֵ�������ַ�ʽ��֧�����кŻ��棬
 * ÿ��ȥ���к�ʱ������Ҫ�������ݿ⣬Ч�ʽϵ͡��Դ��ַ�ʽȡ���к�ʱ�����кŵĲ���ֵӦ�� Ϊ1��
 * </p>
 * 
 */
@Component
public class SysId {
	@Autowired
	private SysSeq sysSeq;

	private static Log log = LogFactory.getLog(SysId.class);

	/** ���к����� */
	public static String SeqNameID = "SEQ_ID";

	// ͼƬ���к�
	public static String seqImageId = "SEQ_IMAGE";

	// ��־���к�
	public static String seqLogId = "SEQ_LOG_ID";

	// ֧�ֶ�����кţ������к����ƺ�ȡ���кŵ�SQL���ڸ�Ӱ����
	// Map(seqName, Map(regionId, PrimaryKey))
	private static Map seqMap = new HashMap();

	/** ͨ���������кŶ����ȡ������:SEQ_ID */
	public Long getId() {
		try {

			return sysSeq.getSequenceItzc(SeqNameID);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** ͨ���������кŶ����ȡ������:SEQ_ID */
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
	 * ͨ���������кŶ����ȡ������
	 * 
	 * @throws Exception
	 */
	private Long getSeq(String seqName, Long regionId) throws Exception {
		String seq = seqName;
		if (seqName == null || seqName.length() <= 0) {
			seq = SeqNameID;
		}

		// ȡ�û�������к�
		PrimaryKey pk = null;
		Object obj = seqMap.get(seq);
		if (obj == null) {
			pk = new PrimaryKey();
			seqMap.put(seq, pk);
		} else {
			pk = (PrimaryKey) obj;
		}

		// ȡ�û�������к�ֵ
		Long id = nextSeq(seq, pk);

		if (log.isDebugEnabled()) {
			log.debug("ȡ�����к�(" + seqName + ")��ֵ��" + id);
		}

		return id;
	}

	// ȡ�û�������к�ֵ
	private Long nextSeq(String seqName, PrimaryKey pk) throws Exception {
		synchronized (pk) {
			Long id = null;
			id = pk.next();
			if (id == null) {
				// û��ȡ�����к�
				try {
					SysSequence po = sysSeq.getSequence(seqName);
					long start = po.getStartValue();
					long end = po.getEndValue();
					pk.setId(start);
					pk.setMaxId(end);
					id = pk.next();
				} catch (Throwable t) {
					throw new Exception("ȡ�����к�ʧ��: " + seqName, t);
				}
			}
			return id;
		}
	}

	// �������кŵ��ڲ���
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
