package com.albanj.capitalize.capitalizeback.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {

	public static Pageable getPageable(Integer pageIndex, Integer pageSize) {
		return getPageable(pageIndex, pageSize, null, false);
	}

	public static Pageable getPageable(Integer pageIndex, Integer pageSize, String sort, boolean ascending) {
		if (pageIndex == null) {
			pageIndex = 0;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		Pageable pageable = null;
		Sort sortBy = null;
		if (sort != null && !sort.equals("")) {
			sortBy = Sort.by(sort);
			if (!ascending) {
				sortBy.descending();
			}
			pageable = PageRequest.of(pageIndex, pageSize, sortBy);
		} else {
			pageable = PageRequest.of(pageIndex, pageSize);
		}
		return pageable;
	}
}
