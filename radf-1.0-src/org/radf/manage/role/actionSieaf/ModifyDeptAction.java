package org.radf.manage.role.actionSieaf;

import java.util.HashMap;

import org.radf.manage.role.entity.SysDept;
import org.radf.manage.role.facade.RoleFacade;
import org.radf.plat.commons.TypeCast;
import org.radf.plat.sieaf.envelop.RequestEnvelop;
import org.radf.plat.sieaf.envelop.ResponseEnvelop;
import org.radf.plat.sieaf.event.Event;
import org.radf.plat.sieaf.event.EventResponse;
import org.radf.plat.util.action.ActionSupport;
import org.radf.plat.util.exception.AppException;
import org.radf.plat.util.exception.ManageInputException;

/**
 * �޸�SYSDEPT��¼
 */
public class ModifyDeptAction extends ActionSupport{
    private String className = this.getClass().getName();

	public ModifyDeptAction() {
	}
	/**
	 * ����RoleFacade.modifyDept()
	 */
	public EventResponse perform(Event evt) {
        EventResponse returnValue = new EventResponse();
        RequestEnvelop value = null;
        
        try {
            //��ȡ�ӿ�ʵ����
            RoleFacade facade = (RoleFacade) getService("RoleFacade");
            //��ڲ���ת��
            value = processEvent(evt);
            
            //���ö�Ӧ��Facadeҵ��������
            ResponseEnvelop resEnv = facade.modifyDept(value);
            
            //���鷵�ز���
            returnValue = processRevt(resEnv);
            
        } catch (ManageInputException me) {
            //��ڲ���ת���г��ֵ��쳣
            returnValue = ExceptionSupport(className,value,me,returnValue);
        } catch (AppException ae) {
            //����FacadeFactory���ֵ��쳣
            returnValue = ExceptionSupport(className,value,ae,returnValue);
        } catch (Exception ex) {
            //�����쳣
            returnValue = ExceptionSupport(className,value,ex,returnValue);
        }
        
        return returnValue;
    }
    /**
     * ϵͳ��ڲ�����װ����
     * ���ݴ����HashMap���ֽ��ȡ��ڲ���������װ������Ҫ�����ʽ
     * �Ϸ����ж���Ҫ�жϳ��ȡ����͡�У��ȣ�һ��ͨ���ͻ�����ɣ�����ֻ�Ƿ�ֹ�ͻ���©�У�����Ҫ�ֶ�����У��
     * @param hashBody
     * @return Object
     * @throws ManageInputException
     */
    protected Object processBody(HashMap hashBody)throws ManageInputException{
		SysDept sysDept = new SysDept();
		sysDept.setDeptName(TypeCast.stringToString((String) hashBody.get("deptName"),"",false));
		sysDept.setDeptPrivilege(TypeCast.stringToString((String) hashBody.get("deptPrivilege"),"�ϼ�����",true));
		sysDept.setComments(TypeCast.stringToString((String) hashBody.get("comments"),"��������",true));
		sysDept.setDeptID(TypeCast.stringToString((String) hashBody.get("deptID"),"",false));

		//validate data
		if (sysDept.getDeptName().length() > 64)
			throw new ManageInputException("deptName���ȱ�������64λ");
		if (sysDept.getDeptPrivilege().length() > 10)
			throw new ManageInputException("DeptPrivilege���ȱ�������10λ");
		if (sysDept.getComments().length() > 64)
			throw new ManageInputException("Comments���ȱ�������64λ");
		if (sysDept.getDeptID().length() > 16)
			throw new ManageInputException("deptID���ȱ�������16λ");

		return sysDept;
	}
    /**
     * @see ActionSupport#processMap(Object);
     */
    protected HashMap processMap(Object body)throws ManageInputException{
        return null;
    }

}