package com.issmart.common.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class GradeUtil {

	/**
	 * 累计停留时间分数计算
	 * 
	 * @param minute
	 * @return
	 */
	public static Integer gradeDurationTime(Integer minute) {
		Integer score = 0;
		if (minute >= 1 && minute < 25) {
			score = 10;
		}
		if (minute >= 15 && minute < 25) {
			score = 15;
		}
		if (minute >= 25) {
			score = 25;
		}
		return score;
	}

	public static Integer gradeSingleBoothDurationTime(Integer minute) {
		Integer score = 0;
		if (1 <= minute && minute < 5) {
			score = 10;
		}
		if (5 <= minute && minute < 15) {
			score = 15;
		}
		if (15 <= minute && minute < 25) {
			score = 20;
		}
		if (25 <= minute) {
			score = 25;
		}
		return score;
	}

	/**
	 * 展台有效互动分数计算
	 * 
	 * @param minute
	 * @return
	 */
	public static Integer gradeInteract(Integer interactTotal) {
		Integer score = 0;
		if (interactTotal >= 15) {
			score = 15;
		} else {
			score = interactTotal;
		}
		return score;
	}

	/**
	 * 累计展台访问数分数计算
	 * 
	 * @param boothRepeatTotal
	 * @return
	 */
	public static Integer gradeBoothRepeat(Integer boothRepeatTotal) {
		Integer score = 0;
		if (boothRepeatTotal >= 15) {
			score = 30;
		} else {
			score = boothRepeatTotal * 2;
		}
		return score;
	}

	public static Integer gradeBooth(Integer boothTotal) {
		Integer score = 0;
		if (boothTotal >= 15) {
			score = 30;
		} else {
			score = boothTotal * 2;
		}
		return score;
	}

	public static void orderBy(List<Map<String, Object>> list) {
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				// TODO Auto-generated method stub
				int ret = 0;
				Integer point1 = (Integer) o1.get("score");
				Integer point2 = (Integer) o2.get("score");
				ret = point2.compareTo(point1);
				return ret;
			}
		});
	}
}
