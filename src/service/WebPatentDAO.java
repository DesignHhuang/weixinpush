package service;

import java.util.List;

import model.Patent;

public interface WebPatentDAO {

	public List<Patent> findWebPatent();
}
