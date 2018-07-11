package dao.support;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sun.net.httpserver.Authenticator.Success;

import dao.CompanyinfoVO;
import dao.ScoreVO;
import dao.StudentinfoVO;
import dao.TeacherinfoVO;
import dao.WeeklyreportVO;
import exception.DBSupportException;

/**
 * DaoSupportʵ����
 *
 * @author zhanguohai Aug 23, 2016
 */
public class StandardDbSupport extends HibernateDaoSupport implements
		IDdSupport {

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#queryAllDataByClass(java.lang.Class)
	 */
	public List<?> queryAllDataByClass(final Class<?> clazz)
			throws DBSupportException {
		try {
			return this.getHibernateTemplate().find("from " + clazz.getName());
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
		return emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#queryAllDataByClass(java.lang.Class,
	 * java.util.Map)
	 */
	public List<?> queryAllDataByClass(final Class<?> clazz,
			Map<String, String> order) throws DBSupportException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + clazz.getName());
		hql.append(" order by ");
		int i = 0;
		for (String key : order.keySet()) {
			String ordervalue = order.get(key);
			if (i != 0) {
				hql.append(" , ");
			}
			hql.append(key + " " + ordervalue);
			i++;
		}

		try {
			return this.getHibernateTemplate().find(hql.toString());
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
		return emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#queryDataByClass(java.lang.Class,
	 * java.lang.String, java.lang.Object)
	 */
	public List<?> queryDataByClass(final Class<?> clazz, String param,
			Object value) throws DBSupportException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + clazz.getName() + " where ");
		hql.append(param + " in (:" + param + ")");

		try {
			return this.getHibernateTemplate().findByNamedParam(hql.toString(),
					param, value);
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
		return emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#queryDataByClass(java.lang.Class,
	 * java.lang.String, java.lang.Object, java.util.Map)
	 */
	public List<?> queryDataByClass(final Class<?> clazz, String param,
			Object value, Map<String, String> order) throws DBSupportException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + clazz.getName() + " where ");
		hql.append(param + " in (:" + param + ")");
		hql.append(" order by ");
		int i = 0;
		for (String key : order.keySet()) {
			String ordervalue = order.get(key);
			if (i != 0) {
				hql.append(" , ");
			}
			hql.append(key + " " + ordervalue);
			i++;
		}

		try {
			return this.getHibernateTemplate().findByNamedParam(hql.toString(),
					param, value);
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
		return emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#queryDataByClass(java.lang.Class,
	 * java.lang.String[], java.lang.Object[])
	 */
	public List<?> queryDataByClass(final Class<?> clazz, String[] param,
			Object[] values) throws DBSupportException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + clazz.getName() + " where ");
		for (int i = 0; i < param.length; i++) {
			if (i != 0) {
				hql.append(" and ");
			}
			hql.append(param[i] + " in (:" + param[i] + ")");
		}
		try {
			return this.getHibernateTemplate().findByNamedParam(hql.toString(),
					param, values);
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
		return emptyList();
	}
//获取对象
	public Object getUpdateObject(String hql,Integer integer) {
		return this.getHibernateTemplate().find(hql,integer);
	}
	
	public List<?> queryNotIn(final Class<?> clazz, String[] param,
			Object[] values) throws DBSupportException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + clazz.getName() + " where ");
		for (int i = 0; i < param.length; i++) {
			if (i != 0) {
				hql.append(" and ");
			}
			if (i == 1) {
				hql.append(param[i] + " not in (:" + param[i] + ")");
			} else {
				hql.append(param[i] + " in (:" + param[i] + ")");
			}
		}
		try {
			return this.getHibernateTemplate().findByNamedParam(hql.toString(),
					param, values);
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
		return emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#queryDataBySql(java.lang.String)
	 */
	@Override
	public List<?> queryDataBySql(String sql) throws DBSupportException {

		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<?> list = session.createSQLQuery(sql).list();
			session.close();
			return list;
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
		return emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#queryDataByHql(java.lang.String)
	 */
	@Override
	public List<?> queryDataByHql(String hql) throws DBSupportException {
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<?> list = session.createQuery(hql).list();
			session.close();
			return list;
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
		return emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#queryDataById(java.lang.Class,
	 * java.io.Serializable)
	 */
	public Object queryDataById(Class<?> model, Serializable id)
			throws DBSupportException {
		if (id == null) {
			return null;
		}

		try {
			return this.getHibernateTemplate().get(model, id);
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
		return null;
	}

	public Object queryDataByNum(final Class<?> clazz, String param,String str)
			throws DBSupportException {
		if (str == null) {
			return null;
		}
		String hql =" from StudentinfoVO where studentNum in ("+str+")";
		try {
			return this.getHibernateTemplate().find(hql);
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
		return null;
	}
	public List queryDataByScore(String clazz,String param1, int value1,int value2)
			throws DBSupportException {
		String hql =" from "+clazz+" where "+param1+" >"+value1+" and "+param1+"<="+value2+"";
		try {
			return this.getHibernateTemplate().find(hql);
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
		return null;
	}
	public String queryParamPageCount(String clazz, String param1,String value1) {
		String hql ="select count(*) from "+clazz+" where "+param1+" like '%"+value1+"%'";
		String str = getHibernateTemplate().find(hql).iterator().next()
				.toString();
		return str;
	}
	public String queryComStuPageCount(String clazz, String param1,String value1) {
		String hql ="select count(*) from "+clazz+" where "+param1+" in ("+value1+")";
		String str = getHibernateTemplate().find(hql).iterator().next()
				.toString();
		return str;
	}
	public String queryPageCount(String clazz) {
		String hql ="select count(*) from "+clazz;
		String str = getHibernateTemplate().find(hql).iterator().next()
				.toString();
		return str;
	}
	public String queryParamPageCount(String clazz, String[] param,
			Object[] values) {
		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) from " + clazz + " where ");
		for (int i = 0; i < param.length; i++) {
			if (i != 0) {
				hql.append(" and ");
			}
			hql.append(param[i] + "='" + values[i] + "'");
		}
		String str = getHibernateTemplate().find(hql.toString()).iterator().next()
				.toString();
		return str;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#queryTotalCount(java.lang.String,
	 * java.util.Map)
	 */
	public int queryTotalCount(String hql, Map<?, ?> map)
			throws DBSupportException {
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			Iterator<?> it = map.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				query.setParameter(key.toString(), map.get(key));
			}
			List<?> list = query.list();
			session.close();
			return list.size();
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#queryByPage(java.lang.String, int, int,
	 * java.util.Map)
	 */
	public List<?> queryByPage(final String hql, final int offset,
			final int length, final Map<?, ?> map) throws DBSupportException {
		try {
			List<?> list = this.getHibernateTemplate().executeFind(
					new HibernateCallback<Object>() {
						@Override
						public Object doInHibernate(Session session)
								throws RuntimeException, SQLException {
							Query query = session.createQuery(hql);
							Iterator<?> it = map.keySet().iterator();
							while (it.hasNext()) {
								Object key = it.next();
								query.setParameter(key.toString(), map.get(key));
							}
							query.setFirstResult((offset - 1) * length);
							query.setMaxResults(length);
							return query.list();
						}

					});
			return list;
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
		return emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#deleteSingleData(java.lang.Object)
	 */
	@Override
	public void deleteSingleData(Object obj) throws DBSupportException {
		try {
			super.getHibernateTemplate().delete(obj);
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#deleteSingleDataById(java.lang.Class,
	 * java.io.Serializable)
	 */
	@Override
	public void deleteSingleDataById(Class<?> clazz, Serializable id)
			throws DBSupportException {
		super.getHibernateTemplate().delete(this.queryDataById(clazz, id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#deleteDatasByCollection(java.util.Collection)
	 */
	@Override
	public void deleteDatasByCollection(Collection<?> entities)
			throws DBSupportException {
		try {
			super.getHibernateTemplate().deleteAll(entities);
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
	}

	/**
	 * 
	 * @param clazz
	 * @param param
	 * @param value
	 * @throws DBSupportException
	 * @throws RuntimeException
	 */
	public void deteleDataByClass(final Class<?> clazz, String param,
			Object value) throws DBSupportException {
		this.getHibernateTemplate().deleteAll(
				queryDataByClass(clazz, param, value));
	}
	public void deteleDataByClass(final Class<?> clazz, String[] param,
			Object[] value) throws DBSupportException {
		this.getHibernateTemplate().deleteAll(
				queryDataByClass(clazz, param, value));
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#saveSingleData(java.lang.Object)
	 */
	@Override
	public Serializable saveSingleData(Object obj) throws DBSupportException {
		try {
			Serializable flag;
			flag = super.getHibernateTemplate().save(obj);
			return flag;
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#saveOrUpdateAllData(java.util.Collection)
	 */
	@Override
	public void saveOrUpdateAllData(Collection<?> allData)
			throws DBSupportException {
		try {
			super.getHibernateTemplate().saveOrUpdateAll(allData);
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#updateSingleData(java.lang.Object)
	 */
	@Override
	public void updateSingleData(Object obj) throws DBSupportException {
		try {
			super.getHibernateTemplate().update(obj);
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#saveOrUpdateSingleData(java.lang.Object)
	 */
	@Override
	public void saveOrUpdateSingleData(Object obj) throws DBSupportException {
		try {
			super.getHibernateTemplate().saveOrUpdate(obj);
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#executeHQL(java.lang.String, java.util.Map)
	 */
	public boolean executeHQL(String hql, Map<?, ?> parameters)
			throws DBSupportException {
		try {

			Session session = this.getSessionFactory().getCurrentSession();
			boolean close = false;
			if (session == null || session.isOpen()) {
				session = this.getSessionFactory().openSession();
				close = true;
			}

			Query query = session.createQuery(hql);

			Iterator<?> it = parameters.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				Object value = parameters.get(key);
				if (value instanceof Collection) {
					query.setParameterList(key.toString(),
							(Collection<?>) value);
				} else {
					query.setParameter(key.toString(), value);
				}
			}
			int i = query.executeUpdate(); // i��ʾ���µ�����

			if (close) {
				session.close();
			}

			return i >= 0 ? Boolean.TRUE : Boolean.FALSE;

		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}

		return Boolean.FALSE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#executeHQL(java.lang.String)
	 */
	@Override
	public boolean executeHQL(String hql) throws DBSupportException {
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			boolean close = false;
			if (session == null || session.isOpen()) {
				session = this.getSessionFactory().openSession();
				close = true;
			}
			Query query = session.createQuery(hql);
			int i = query.executeUpdate();
			if (close) {
				session.close();
			}
			return i >= 0 ? Boolean.TRUE : Boolean.FALSE;
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}

		return Boolean.FALSE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#executeSQL(java.lang.String)
	 */
	@Override
	public boolean executeSQL(String sql) throws DBSupportException {
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			boolean close = false;
			if (session == null || session.isOpen()) {
				session = this.getSessionFactory().openSession();
				close = true;
			}
			Query query = session.createSQLQuery(sql);
			int i = query.executeUpdate(); // i��ʾ���µ�����
			if (close) {
				session.close();
			}
			return i >= 0 ? Boolean.TRUE : Boolean.FALSE;
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}

		return Boolean.FALSE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#executeSQLQuery(java.lang.String,
	 * java.util.List)
	 */
	public List<?> executeSQLQuery(final String sql, final List<Object> params)
			throws DBSupportException {
		try {
			return (List<?>) this.getHibernateTemplate().execute(
					new HibernateCallback<Object>() {
						public Object doInHibernate(final Session session)
								throws RuntimeException, SQLException {
							final SQLQuery query = session.createSQLQuery(sql);

							if (params != null) {
								for (int i = 0; i < params.size(); i++) {
									query.setParameter(i, params.get(i));
								}
							}
							return query.list();
						}
					});
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}

		return emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.support.IDbSupport#clearCache()
	 */
	@Override
	public void clearCache() throws DBSupportException {
		try {
			getHibernateTemplate().clear();
		} catch (RuntimeException e) {
			handleRuntimeException(e);
		}
	}

	/**
	 * �����µ�session����Ҫ�ֶ��ر�
	 * 
	 * @return
	 */
	public Session newSession() {
		return this.getSessionFactory().openSession();
	}

	/**
	 * ��RuntimeExceptionתΪDBSupportException�׳�
	 * 
	 * @param e
	 * @throws DBSupportException
	 */
	private static void handleRuntimeException(RuntimeException e)
			throws DBSupportException {
		throw new DBSupportException(e);
	}

	/**
	 * ���ؿ��б�
	 * 
	 * @return
	 */
	private static List<?> emptyList() {
		return new ArrayList<Object>();
	}

	@SuppressWarnings("unchecked")
	public List<StudentinfoVO> setPageAll(int start, int limit) {
		try {
			final int starts = start;
			final int sizes = limit;
			final String queryString = "from StudentinfoVO";
			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws SQLException {
					List list2 = session.createQuery(queryString)
							.setFirstResult((starts - 1) * sizes)
							.setMaxResults(sizes).list();
					return list2;
				}
			});
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<CompanyinfoVO> setPageCmpAll(int start, int limit) {
		try {
			final int starts = start;
			final int sizes = limit;
			final String queryString = "from CompanyinfoVO";
			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws SQLException {
					List list2 = session.createQuery(queryString)
							.setFirstResult((starts - 1) * sizes)
							.setMaxResults(sizes).list();
					return list2;
				}
			});
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TeacherinfoVO> setPageTeaAll(int start, int limit) {
		try {
			final int starts = start;
			final int sizes = limit;
			final String queryString = "from TeacherinfoVO";
			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws SQLException {
					List list2 = session.createQuery(queryString)
							.setFirstResult((starts - 1) * sizes)
							.setMaxResults(sizes).list();
					return list2;
				}
			});
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List setPageData(final Class<?> clazz, String param, Object value,
			int start, int limit) {
		try {
			final int starts = start;
			final int sizes = limit;
			final String queryString = "from " + clazz.getName() + " where "
					+ param + " like" + "'%" + value + "%'";
			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws SQLException {
					List list2 = session.createQuery(queryString)
							.setFirstResult((starts - 1) * sizes)
							.setMaxResults(sizes).list();
					return list2;
				}
			});
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public List setPageData1(final Class<?> clazz, final String param, final Object value,
			int start, int limit) {
		try {
			final int starts = start;
			final int sizes = limit;
			final String hql =("from " + clazz.getName() + " where "+param+" in (:" + param + ")");
			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws SQLException {
					Query query =session.createQuery(hql);
					query.setParameter(param, value);
					List list2 =query.setFirstResult((starts - 1) * sizes)
					.setMaxResults(sizes).list();
					return list2;
				}
			});
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<WeeklyreportVO> setPageData(final Class<?> clazz, final String param[], final Object[] value,
			int start, int limit) {
		try {
			final int starts = start;
			final int sizes = limit;
			final StringBuffer queryString = new StringBuffer();
			queryString.append("from " + clazz.getName() + " where ");
			for (int i = 0; i < param.length; i++) {
				if (i != 0) {
					queryString.append(" and ");
				}
				if (i == 1) {
					queryString.append(param[i] + " not in (:" + param[i] + ")");
				} else {
					queryString.append(param[i] + " in (:" + param[i] + ")");
				}
			}
			queryString.append(" order by reportStatus desc");
			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws SQLException {
					Query query =session.createQuery(queryString.toString());
					query.setParameter(param[0], value[0]);
					query.setParameter(param[1], value[1]);
					List list2 =query.setFirstResult((starts - 1) * sizes)
					.setMaxResults(sizes).list();
					return list2;
				}
			});
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
