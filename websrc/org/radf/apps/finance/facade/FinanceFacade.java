package org.radf.apps.finance.facade;

import java.sql.SQLException;
import java.util.List;

import org.radf.apps.commons.entity.Finance;
import org.radf.plat.sieaf.envelop.RequestEnvelop;
import org.radf.plat.sieaf.envelop.ResponseEnvelop;

public interface FinanceFacade {

	public ResponseEnvelop save(RequestEnvelop requestEnvelop);

	public List<Finance> createfin(String fintype, String notype,
			String[] ListTypeno, String[] ListGctnm, String[] ListGctid,
			String[] ListIctnm, String[] ListIctid, String[] ListPdtid,
			String[] ListPdtnm, String[] ListBand, String[] ListTmksid,
			String[] ListChgdt, String[] listRedt, String[] ListPdtprc,
			String[] ListPdtnum, String[] ListFinrate,
			String[] ListFinprccount, String[] ListFinprc,
			String[] listInvoicecode, String[] listInvoiceno,
			String[] listSellprc, String[] listRetailname,
			String[] listRetailtaxno, String[] listComaddr,
			String[] listDepositbank, String[] listDepositid,
			String[] listSpecialtel);

	public boolean isTimeOK();

	public List<Finance> createfin(String fintype, String notype,
			String typeno, String gctnm, String gctid, String ictnm,
			String pdtid, String pdtnm, String band, String tmksid,
			String chgdt, String redt, String pdtprc, String pdtnum,
			String finrate, String finprccount, String finprc,
			String invoicecode, String invoiceno, String sellprc);

	public List<Finance> createNotBillFin() throws SQLException;

	public boolean save(List<Finance> financeList) throws Exception;

	// public boolean savetest() throws Exception;

	public boolean ifHaveFin(String fintype, String listTypeno[],
			String listPdtid[]) throws Exception;

	public void revokeFin(String[] listFinno, String[] listFintype,
			String[] listNotype, String[] listGctid, String[] listPdtid,
			String[] listPdtnum, String[] listFinnt) throws Exception;

}
