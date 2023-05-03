using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Web;
using System.Web.Mvc;

namespace EHealthCare.Areas.Hospital.Controllers
{
    public class BaseController : Controller
    {
        protected Nullable<int> HospitalId
        {
            get
            {
                if (Session["HospitalId"] != null && Convert.ToInt32(Session["HospitalId"]) > 0)
                    return Convert.ToInt32(Session["HospitalId"]);

                else return null;
            }
        }

        protected string GetAPICall(string Url)
        {
            HttpClient client = Connection();

            HttpResponseMessage message = client.GetAsync(Url).Result;

            HttpContent response = message.Content;

            return response.ReadAsStringAsync().Result;
        }

        protected string PostAPICall<EHealthCare>(string Url, EHealthCare Body)
        {
            HttpClient client = Connection();

            StringContent content = new StringContent(JsonConvert.SerializeObject(Body), Encoding.UTF8, "application/json");

            HttpResponseMessage message = client.PostAsync(Url, content).Result;

            HttpContent response = message.Content;

            return response.ReadAsStringAsync().Result;
        }

        protected string PostAPIImage(string Url, HttpPostedFileBase file)
        {
            HttpClient client = Connection();

            //MultipartFormDataContent data = new MultipartFormDataContent();
            //data.Add(new StreamContent(file.InputStream), "image", file.FileName);

            //HttpResponseMessage message = client.PostAsync(Url, data).Result;

            //HttpContent response = message.Content;

            using (var formData = new MultipartFormDataContent())
            {
                byte[] imgData;
                using (var reader = new BinaryReader(file.InputStream))
                {
                    imgData = reader.ReadBytes(file.ContentLength);
                }

                formData.Add(new StreamContent(new MemoryStream(imgData)), "image", file.FileName);

                HttpContent response = client.PostAsync(Url, formData).Result.Content;

                return response.ReadAsStringAsync().Result;
            }
        }

            private HttpClient Connection()
            {
                HttpClient client = new HttpClient();

                client.BaseAddress = new Uri(System.Configuration.ConfigurationManager.AppSettings["APIKey"].ToString());

                return client;
            }
        }
    }