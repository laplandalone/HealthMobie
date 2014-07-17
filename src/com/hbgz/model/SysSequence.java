package com.hbgz.model;

public class SysSequence 
{
	private static final long serialVersionUID = -1739791509869550469L;

	// Ö÷¼üÊôÐÔ£ºID
	private Long id;

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	private String className;

	private Long seqId;

	private Integer stepValue;

	private Long minValue;

	private Long maxValue;

	private long startValue;

	private long endValue;

	public long getEndValue() 
	{
		return endValue;
	}

	public void setEndValue(long endValue) 
	{
		this.endValue = endValue;
	}

	public long getStartValue() 
	{
		return startValue;
	}

	public void setStartValue(long startValue) 
	{
		this.startValue = startValue;
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className) 
	{
		this.className = className;
	}

	public Long getMaxValue() 
	{
		return maxValue;
	}

	public void setMaxValue(Long maxValue)
	{
		this.maxValue = maxValue;
	}

	public Long getMinValue() 
	{
		return minValue;
	}

	public void setMinValue(Long minValue) 
	{
		this.minValue = minValue;
	}

	public Long getSeqId() 
	{
		return seqId;
	}

	public void setSeqId(Long seqId) 
	{
		this.seqId = seqId;
	}

	public Integer getStepValue() 
	{
		return stepValue;
	}

	public void setStepValue(Integer stepValue) 
	{
		this.stepValue = stepValue;
	}
}
