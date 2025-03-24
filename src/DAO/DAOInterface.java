package DAO;

import java.util.ArrayList;

public interface DAOInterface<T> {
	public int insert(T t);
	public int delete(T t);
	public int update(T t);
	public ArrayList<T> getAll();
}
