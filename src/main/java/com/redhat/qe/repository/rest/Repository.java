package com.redhat.qe.repository.rest;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.calgb.test.performance.HttpSession;
import org.calgb.test.performance.ProcessResponseBodyException;
import org.calgb.test.performance.RequestException;

import com.redhat.qe.model.Model;

public abstract class Repository<T extends Model> {
	public HttpSession session;
	private Unmarshaller unmarshaller;

	public Repository(HttpSession session) {
		createUnmarshaller();
		this.session = session;
	}

	/**
	 * 
	 */
	private void createUnmarshaller() {
		try {
			this.unmarshaller = JaxbContext.getContext().createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException("unable to create unmarshaller");
		}
	}

	/**
	 * @return the session
	 */
	public HttpSession getSession() {
		return session;
	}

	/**
	 * @param session
	 *            the session to set
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}

	/**
	 * @return the unmarshaller
	 */
	public Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}

	public ResponseWrapper sendTransaction(HttpRequestBase request) {
		try {
			return new ResponseWrapper(getSession().sendTransaction(request));
		} catch (ProcessResponseBodyException e) {
			throw new RuntimeException(e);
		} catch (RequestException e) {
			throw new RuntimeException(e);
		}
	}

	public Object unmarshal(String raw) {
		try {
			return getUnmarshaller().unmarshal(new StringReader(raw));
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public String marshall(Object object) {
		try {
			return com.redhat.qe.helpers.jaxb.MyMarshaller.marshall(JaxbContext.getContext(), object);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	T create(T entity, String path) {
		String xml = marshall(entity);
		HttpPost post = new PostRequestFactory().createPost(path, xml);
		ResponseWrapper response = sendTransaction(post);
		response.expectCode(201);
		@SuppressWarnings("unchecked")
		T result = (T) unmarshal(response.getBody());
		return result;
	}

	ResponseWrapper delete(T entity, String collectionPath) {
		ResponseWrapper response = _delete(entity, collectionPath);
		response.expectCode(200);
		return response;
	}

	/**
	 * @param entity
	 * @param collectionPath
	 * @return
	 */
	protected ResponseWrapper _delete(T entity, String collectionPath) {
		return sendTransaction(new HttpDelete(memberPath(
		entity, collectionPath)));
	}

	protected ResponseWrapper customAction(T entity, String collectionPath, String action) {
		ResponseWrapper response = _customAction(entity, collectionPath, action);
		response.expectCode(200);
		return response;
	}

	/**
	 * @param entity
	 * @param collectionPath
	 * @param action
	 * @return
	 */
	protected ResponseWrapper _customAction(T entity, String collectionPath, String action) {
		return sendTransaction(new PostRequestFactory()
				.createPost(customActionPath(entity, collectionPath, action),
						"<action />"));
	}

	/**
	 * @param entity
	 * @param collectionPath
	 * @param action
	 * @return
	 */
	protected String customActionPath(T entity, String collectionPath,
			String action) {
		return memberPath(entity, collectionPath) + "/" + action;
	}

	public ResponseWrapper customAction(T entity, String collectionPath, String actionName,Marshallable action) {
		return sendTransaction(new PostRequestFactory()
				.createPost(customActionPath(entity, collectionPath, actionName), marshall(action)));
	}

	/**
	 * @param entity
	 * @param path
	 * @return
	 */
	private String memberPath(T entity, String path) {
		return String.format(path + "/%s", entity.getId());
	}

	T show(T entity, String path) {
		ResponseWrapper response = _show(entity, path);
		response.expectCode(200);
		@SuppressWarnings("unchecked")
		T result = (T) unmarshal(response.getBody());
		return result;
	}

	ResponseWrapper _show(T entity, String collectionPath) {
		String memberPath = memberPath(entity, collectionPath);
		ResponseWrapper response = _httpGet(memberPath);
		return response;
	}

	boolean _isExists(T entity, String collectionPath) {
		if (_show(entity, collectionPath).getCode() == 404)
			return false;
		else
			return true;
	}

	protected abstract ArrayList<T> deserializeCollectionXmlToList(String raw);

	ArrayList<T> list(String collectionPath) {
		ResponseWrapper response = _list(collectionPath);
		return deserializeCollectionXmlToList(response.getBody());
	}

	/**
	 * @param collectionPath
	 * @return
	 */
	ResponseWrapper _list(String collectionPath) {
		return _httpGet(collectionPath);
	}

	ResponseWrapper _httpGet(String collectionPath) {
		return sendTransaction(new HttpGet(collectionPath));
	}

	boolean isExist(T entity, String collectionPath) {
		ArrayList<T> list = list(collectionPath);
		return list != null  && list.size() > 0  && list.contains(entity);
	}
	
	T createOrShow(T entity, String collectionPath){
		if(isExist(entity, collectionPath)){
			ArrayList<T> resources = list(collectionPath);
			int resultIdx = resources.indexOf(entity);
			return resources.get(resultIdx); 

		}else{
			return create(entity, collectionPath);
		}
		
	}

}
