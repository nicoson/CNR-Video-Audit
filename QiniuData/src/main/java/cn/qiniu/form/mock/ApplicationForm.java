package cn.qiniu.form.mock;

public class ApplicationForm {
	private boolean wait;
	private ApplicationData data;
	
	/**
	 * @return the wait
	 */
	public boolean getWait() {
		return wait;
	}

	/**
	 * @param wait the wait to set
	 */
	public void setWait(boolean wait) {
		this.wait = wait;
	}

	/**
	 * @return the data
	 */
	public ApplicationData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(ApplicationData data) {
		this.data = data;
	}

	public class ApplicationData {
		private String uri;

		/**
		 * @return the uri
		 */
		public String getUri() {
			return uri;
		}

		/**
		 * @param uri the uri to set
		 */
		public void setUri(String uri) {
			this.uri = uri;
		}
	}
}
