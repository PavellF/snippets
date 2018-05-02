package theObserver.standard;

import java.math.BigDecimal;
import java.util.Date;

public class DataNode {

	private BigDecimal changeRate;
	
	private Date operationDate;

	public BigDecimal getChangeRate() {
		return changeRate;
	}

	public DataNode setChangeRate(BigDecimal changeRate) {
		this.changeRate = changeRate;
		return this;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public DataNode setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((changeRate == null) ? 0 : changeRate.hashCode());
		result = prime * result + ((operationDate == null) ? 0 : operationDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataNode other = (DataNode) obj;
		if (changeRate == null) {
			if (other.changeRate != null)
				return false;
		} else if (!changeRate.equals(other.changeRate))
			return false;
		if (operationDate == null) {
			if (other.operationDate != null)
				return false;
		} else if (!operationDate.equals(other.operationDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataNode [changeRate=" + changeRate + ", operationDate=" + operationDate + "]";
	}
	
	
	
}
