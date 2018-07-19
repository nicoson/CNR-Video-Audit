//package cn.qiniu.delegator.service;
//
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.apache.commons.codec.binary.Base64;
//import org.springframework.stereotype.Service;
//
//@Service
//public class QiniuTokenService {
//
//	private static final String HMAC_SHA1 = "HmacSHA1";
//
//	/**
//	 * 生成签名数据
//	 * 
//	 * @param data
//	 *            待加密的数据
//	 * @param key
//	 *            加密使用的key
//	 * @return 生成encodedSign字符串
//	 * @throws InvalidKeyException
//	 * @throws NoSuchAlgorithmException
//	 */
//	public String getSignature(String Data, String secretKey)
//			throws InvalidKeyException, NoSuchAlgorithmException {
//		byte[] data = Data.getBytes();
//		byte[] key = secretKey.getBytes();
//		SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
//		Mac mac = Mac.getInstance(HMAC_SHA1);
//		mac.init(signingKey);
//		byte[] sign = mac.doFinal(data);
////		String encodedSign = new String(Base64.encodeBase64URLSafe(sign));
//		String encodedSign =Base64.encodeBase64URLSafeString(sign);
//		String Access_Key="HK1vOltLkw0HQ7pqCYuoqGwm7XTgcaxhQ7qejE1Y";
//		String QiniuToken = "Qiniu " + Access_Key + ":" + encodedSign;
//		return QiniuToken;
//	}
//
//	/**
//	 * 获取加密字符串
//	 *
//	 * @return(String)
//	 */
//	// public String getBASE64(String sign) {
//	// if (sign == null) {
//	// return null;
//	// }
//	// return (new Base64Encoder()).encode(sign.getBytes());
//	// }
//
//}
