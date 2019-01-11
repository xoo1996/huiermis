package org.radf.manage.role.actionSieaf;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.radf.manage.role.entity.SysDept;
import org.radf.manage.role.facade.RoleFacade;
import org.radf.plat.sieaf.envelop.RequestEnvelop;
import org.radf.plat.sieaf.envelop.ResponseEnvelop;
import org.radf.plat.sieaf.event.Event;
import org.radf.plat.sieaf.event.EventResponse;
import org.radf.plat.util.action.ActionSupport;
import org.radf.plat.util.exception.AppException;
import org.radf.plat.util.exception.ManageInputException;
/**
 * 查询所有的SYSDEPT记录
 */
public class FindAllDeptsAction extends ActionSupport{
    private String className = this.getClass().getName();
       /**
        * 调用RoleFacade.findAllDepts()
        */
	public EventResponse perform(Event evt) {
        EventResponse returnValue = new EventResponse();
        RequestEnvelop value = null;
        
        try {
            //获取接口实现类
            RoleFacade facade = (RoleFacade) getService("RoleFacade");
            //入口参数转换
            value = processEvent(evt);
            
            //调用对应的Facade业务处理方法
            ResponseEnvelop resEnv = facade.findAllDepts(value);
            
            //重组返回参数
            returnValue = processRevt(resEnv);
            
        } catch (ManageInputException me) {
            //入口参数转换中出现的异常
            returnValue = ExceptionSupport(className,value,me,returnValue);
        } catch (AppException ae) {
            //创建FacadeFactory出现的异常
            returnValue = ExceptionSupport(className,value,ae,returnValue);
        } catch (Exception ex) {
            //其它异常
            returnValue = ExceptionSupport(className,value,ex,returnValue);
        }       
        return returnValue;
	}

    
    /**
     * 根据传入的ResponseEnvelop.getBody()内容，生成返回参数EventResponse.setBody内容
     * @param Object body ResponseEnvelop.getBody()部分
     * @return HashMap
     */
    protected HashMap processMap(Object resBody){
        Collection collection = (Collection) resBody;
        
        Collection list = new Vector();
        HashMap body = new HashMap();
        HashMap row2 = new HashMap();
      
        for (Iterator iter = collection.iterator(); iter.hasNext();) {
            SysDept sysDept = (SysDept) iter.next();
            HashMap row = new HashMap();

            String deptID = sysDept.getDeptID();
            String deptName = sysDept.getDeptName();
            String deptPrivilege = sysDept.getDeptPrivilege();
            String comments = sysDept.getComments();

           
            //row.put("roleID",      "1");
           
            row.put("groupID",      deptID);
            row.put("deptID",       deptID);            //机构编号
            row.put("deptName",     deptName);          //机构名称
            row.put("deptPrivilege",deptPrivilege);     //上级机构编码
            row.put("comments",     comments);
            list.add(row);
        }
        body.put("allDepts", list);
       return body;
    }
    /**
     * @see ActionSupport#processBody(HashMap);
     */
    protected Object processBody(HashMap hashBody) throws ManageInputException {
        return null;
    }

}
