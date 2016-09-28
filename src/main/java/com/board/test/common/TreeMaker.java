package com.board.test.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class TreeMaker {
	public String makeTreeByHierachy(List<TreeVO> list){
		List<TreeVO> rootlist = new ArrayList<TreeVO>();
		int listSize = list.size();
		TreeVO trv1,trv2;
		for(int i = 0; i<listSize; i++){
			trv1 = list.get(i);
			
			if(trv1.getParent() == null){
				rootlist.add(trv1);
				continue;
			}
			
			for(int j=0; j<listSize; j++){
				trv2 = list.get(j);
				if(trv1.getParent().equals(trv2.getKey())){
					if(trv2.getChildren() == null) trv2.setChildren(new ArrayList<TreeVO>());
					List<TreeVO> a = trv2.getChildren();
					a.add(trv1);
					trv2.setFolder(true);
					break;
				}
			}
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String str ="";
		try{
			str = mapper.writeValueAsString(rootlist);
		} catch(IOException e){
			e.printStackTrace();
		}
		return str;
	}
}
