package com.neural.jam.endpoints.shared;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class OffsetLimitPageable implements Pageable {
	private Long offset;
	private int limit;
	private String columnName;
	private Direction direction;
	private String filter;
	private String search;
	
	private static Integer maxLimit = 1000;

	private Sort sort;
	private static Long defaultOffset = 0l;
	private static Integer defaultLimit = 100;

	public OffsetLimitPageable(Long offset, Integer limit, Sort sort) {
		this.offset = offset == null ? OffsetLimitPageable.defaultOffset : offset;
		this.limit = limit == null ? OffsetLimitPageable.defaultLimit : (limit > maxLimit ? maxLimit : limit);
		this.sort = sort;

		if (this.offset < 0 || this.limit < 1) {
			throw new IllegalArgumentException(
					"Offset index must be greater than zero and Limit must be greater than one.");
		}
	}

	public OffsetLimitPageable(Long offset, Integer limit, String direction, String columnName) {
		this(offset, limit, Sort.unsorted());
		
		try {
			this.direction = Direction.fromString(direction);
		}catch(Exception e) {
			this.direction = Direction.ASC;
		}
		this.columnName = columnName;
		
		if(columnName == null)
			return;
	}
	
	public OffsetLimitPageable(Long offset, Integer limit, String direction, String columnName, String filter) {
		this(offset, limit, direction, columnName);
		this.filter = filter;
	}
	
	public OffsetLimitPageable(Long offset, Integer limit, String direction, String columnName, String filter, String search) {
		this(offset, limit, direction, columnName, filter);
		this.search = search;
	}

	public OffsetLimitPageable() {
		this(defaultOffset, maxLimit, Sort.unsorted());
	}
	
	public OffsetLimitPageable(Long offset, Integer limit) {
		this(offset, limit, Sort.unsorted());
	}

	public OffsetLimitPageable(String columnName) {
		this(null, null, null, columnName);
	}

	public OffsetLimitPageable(Long offset, Integer limit, String columnName) {
		this(offset, limit, null, columnName);
	}

	@Override
	public int getPageNumber() {
		return (int)(offset / limit);
	}

	@Override
	public int getPageSize() {
		return limit;
	}

	@Override
	public long getOffset() {
		return offset;
	}

	@Override
	public Sort getSort() {
		return this.sort;
	}

	public String getColumName() {
		return this.columnName;
	}
	
	public void setColumName(String columnName) {
		this.columnName = columnName;
	}

	public Direction getDirectionAsDirection() {
		return this.direction;
	}
	
	public Pageable nextOrNull (int pageNumber, long totalCount) {
		if((pageNumber + 1) * this.getLimit() < totalCount) {
			this.offset = (long)(pageNumber + 1) * this.getLimit();
			return this;
		}
		else
			return null;
	}

	@Override
	public Pageable next() {
		return new OffsetLimitPageable( getOffset() + getPageSize(), getPageSize(), getSort());
	}

	public OffsetLimitPageable previous() {
		return hasPrevious() ? new OffsetLimitPageable(getOffset() - getPageSize(), getPageSize(), getSort()) : this;
	}

	@Override
	public Pageable previousOrFirst() {
		return hasPrevious() ? previous() : first();
	}

	@Override
	public Pageable first() {
		return new OffsetLimitPageable(0l, getPageSize(), getSort());
	}

	@Override
	public boolean hasPrevious() {
		return offset > limit;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
