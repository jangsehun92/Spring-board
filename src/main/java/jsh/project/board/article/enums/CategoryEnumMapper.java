package jsh.project.board.article.enums;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CategoryEnumMapper {
	
	public CategoryEnumMapper() {
		
	}
	
	public String getCategory(String category) {
		return AllCategory.valueOf(category.toUpperCase()).getValue();
	}
	
	public boolean isNoticeCategory(String category) {
		boolean result = false;
		for(AdminCategory adminCategorys : AdminCategory.values()) {
			if(adminCategorys.getKey().equals(category.toUpperCase())) {
				result = true;
			}
		}
		return result;
	}
	
	public List<CategoryDto> getCategorys(String category){
		if(isNoticeCategory(category)) {
			return getAdminCategory();
		}
		return getUserCategory();
	}
		
	
	public List<CategoryDto> getAdminCategory() {
		List<CategoryDto> list = new ArrayList<CategoryDto>();
		for(AdminCategory adminCategorys : AdminCategory.values()) {
			list.add(new CategoryDto(adminCategorys));
		}
		return list;
	}
	
	public List<CategoryDto> getUserCategory(){
		List<CategoryDto> list = new ArrayList<CategoryDto>();
		for(UserCategory userCategorys : UserCategory.values()) {
			list.add(new CategoryDto(userCategorys));
		}
		return list;
	}
	

}
