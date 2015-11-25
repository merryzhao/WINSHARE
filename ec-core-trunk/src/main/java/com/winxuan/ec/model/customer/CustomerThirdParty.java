package com.winxuan.ec.model.customer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.user.Customer;

/**
 * description
 * 
 * @author Caoyowuen
 * @version 1.0,2013-8-30
 */
@Entity
@Table(name="customer_thirduser_relation")
public class CustomerThirdParty {
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;

		@JoinColumn(name = "thirdcustomer")
		@OneToOne
		private Customer thirdparty;
	    
		@JoinColumn(name="customer")
	    @OneToOne
		private Customer winxuan;
	    
	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
	    /**
	     * 第三方账号
	     */
		public Customer getThirdparty() {
			return thirdparty;
		}

		public void setThirdparty(Customer thirdparty) {
			this.thirdparty = thirdparty;
		}

		 /**
	     * 文轩账号
	     */
		public Customer getWinxuan() {
			return winxuan;
		}

		public void setWinxuan(Customer winxuan) {
			this.winxuan = winxuan;
		}

		@Override
		public String toString() {
			return "CustomerThirdParty [id=" + id + ", thirdparty="
					+ thirdparty + ", winxuan=" + winxuan + "]";
		}
		
}
