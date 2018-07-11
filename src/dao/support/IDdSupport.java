package dao.support;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import exception.DBSupportException;

/**
 * 数据库操作基础接口
 *
 * @author Athrun tang.pm@cfischina.com
 * Jul 23, 2015
 */
public interface IDdSupport {
	
	/**
	 * 根据CLASS返回所有对象
	 * @param clazz
	 * @return
	 * @throws DBSupportException 
	 */
	public List<?> queryAllDataByClass(final Class<?> clazz) throws DBSupportException;
	
	/**
	 * 根据CLASS返回所有对象(排序)
	 * @param clazz
	 * @param order
	 * @return
	 * @throws DBSupportException 
	 */
	public List<?> queryAllDataByClass(final Class<?> clazz,Map<String,String> order) throws DBSupportException;
	
	/**
	 * 根据CLASS 以及单个参数返回对象List
	 * @param clazz
	 * @param param
	 * @param value
	 * @return
	 * @throws DBSupportException 
	 */
	public List<?> queryDataByClass(final Class<?> clazz,String param ,Object value) throws DBSupportException;
	
	/**
	 * 根据CLASS 以及单个参数返回对象List（带排序）
	 * @param clazz
	 * @param param
	 * @param value
	 * @param order
	 * @return
	 * @throws DBSupportException 
	 */
	public List<?> queryDataByClass(final Class<?> clazz,String param ,Object value,Map<String,String> order) throws DBSupportException;
	
	/**
	 * 根据CLASS 以及多个参数返回对象List
	 * @param clazz
	 * @param param
	 * @param values
	 * @return
	 * @throws DBSupportException 
	 */
	public List<?> queryDataByClass(final Class<?> clazz,String[] param ,Object[] values) throws DBSupportException;
	
	/**
	 * 使用sql查询数据
	 * @param sql
	 * @return
	 * @throws DBSupportException 
	 */
	public List<?> queryDataBySql(String sql) throws DBSupportException;
	
	/**
	 * 使用Hql查询数据
	 * @param hql
	 * @return
	 * @throws DBSupportException 
	 */
	public List<?> queryDataByHql(String hql) throws DBSupportException;
	
	/**
	 * 根据ID获取对象
	 * @param model
	 * @param id
	 * @return
	 * @throws DBSupportException 
	 */
	public Object queryDataById(Class<?> model, Serializable id) throws DBSupportException;
	
	/**
	 * 根据查询条件查询记录数的个数
	 * @param hql
	 * @param map
	 * @return
	 * @throws DBSupportException 
	 */
	public int queryTotalCount(String hql, Map<?, ?> map) throws DBSupportException;
	
	/**
	 * 分页查询
	 * @param hql
	 * @param offset
	 * @param length
	 * @param map
	 * @return
	 * @throws DBSupportException
	 */
	public List<?> queryByPage(final String hql, final int offset, final int length, final Map<?, ?> map) throws DBSupportException;
	
	/**
	 * 删除单个对象
	 * @param obj
	 * @throws DBSupportException 
	 */
	public void deleteSingleData(Object obj) throws DBSupportException ;
	
	/**
	 * 根据ID删除单个对象
	 * @param model
	 * @param id
	 * @throws DBSupportException
	 */
	public void deleteSingleDataById(Class<?> model, Serializable id) throws DBSupportException;
	
	/**
	 * 删除集合
	 * @param entities
	 * @throws DBSupportException
	 */
	public void deleteDatasByCollection(Collection<?> entities) throws DBSupportException;
	
	/**
	 * 添加单条数据
	 * @param obj
	 * @return
	 * @throws DBSupportException
	 */
	public Serializable saveSingleData(Object obj) throws DBSupportException;
	
	/**
	 * 添加集合数据
	 * @param allData
	 * @throws DBSupportException
	 */
	public void saveOrUpdateAllData(Collection<?> allData) throws DBSupportException;
	
	/**
	 * 修改单条记录
	 * @param obj
	 * @throws DBSupportException
	 */
	public void updateSingleData(Object obj) throws DBSupportException;
	
	/**
	 * 保存或修改记录
	 * @param obj
	 */
	public void saveOrUpdateSingleData(Object obj) throws DBSupportException;
	
	/**
	 * 执行Hql命令
	 * @param hql
	 * @param parameters
	 * @return
	 * @throws DBSupportException
	 */
	public boolean executeHQL(String hql,Map<?, ?> parameters) throws DBSupportException;
	
	/**
	 * 执行HQL语句
	 * @param hql
	 * @return
	 * @throws DBSupportException
	 */
	public boolean executeHQL(String hql) throws DBSupportException;
	
	/**
	 * 执行SQL语句
	 * @param sql
	 * @return
	 * @throws DBSupportException
	 */
	public boolean executeSQL(String sql) throws DBSupportException;
	
	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws DBSupportException
	 */
	public List<?> executeSQLQuery(final String sql, final List<Object> params) throws DBSupportException;
	
	
	/**
	 * 清除缓存
	 * @throws DBSupportException
	 */
	public void clearCache() throws DBSupportException;
	
	
}
