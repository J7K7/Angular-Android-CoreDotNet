using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Web;

namespace EHealthCare.Providers
{
    public class AuthenticationProvider
    {
        public static string Encrypt(string text)
        {
            TripleDESCryptoServiceProvider cryptDES3 = new TripleDESCryptoServiceProvider();
            MD5CryptoServiceProvider cryptMD5Hash = new MD5CryptoServiceProvider();
            string key = "EHe@!th(@re";
            cryptDES3.Key = cryptMD5Hash.ComputeHash(ASCIIEncoding.ASCII.GetBytes(key));
            cryptDES3.Mode = CipherMode.ECB;
            ICryptoTransform desdencrypt = cryptDES3.CreateEncryptor();
            byte[] buff = ASCIIEncoding.ASCII.GetBytes(text);
            string Encrypt = Convert.ToBase64String(desdencrypt.TransformFinalBlock(buff, 0, buff.Length));
            Encrypt = Encrypt.Replace("+", "!");
            return Encrypt;
        }

        public static string Decrypt(string text)
        {
            TripleDESCryptoServiceProvider cryptDES3 = new TripleDESCryptoServiceProvider();
            MD5CryptoServiceProvider cryptMD5Hash = new MD5CryptoServiceProvider();
            string key = "EHe@!th(@re";

            text = text.Replace("!", "+");
            byte[] buf = new byte[text.Length];
            cryptDES3.Key = cryptMD5Hash.ComputeHash(ASCIIEncoding.ASCII.GetBytes(key));
            cryptDES3.Mode = CipherMode.ECB;
            ICryptoTransform desdencrypt = cryptDES3.CreateDecryptor();
            buf = Convert.FromBase64String(text);
            string Decrypt = ASCIIEncoding.ASCII.GetString(desdencrypt.TransformFinalBlock(buf, 0, buf.Length));
            return Decrypt;
        }
    }
}