package jsh.project.board.article.enums;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CategoryEnumMapper {
	
	public CategoryEnumMapper() {
		
	}
	
	public String getCategory(String category) {
		return AllCategory.valueOf(category.toUpperCase()).getCategory();
	}
	
	public List<CategoryDto> getCategorys(String category){
		boolean result = false;
		for(AdminCategory adminCategorys : AdminCategory.values()) {
			if(adminCategorys.getValue().equals(category)) {
				result = true;
			}
		}
		
		if(result) {
			return getAdminCategory();
		}else {
			return getUserCategory();
		}
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
