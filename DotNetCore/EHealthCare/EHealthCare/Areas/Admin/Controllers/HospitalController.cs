using EHealthCare.App_Start;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace EHealthCare.Areas.Admin.Controllers
{
    public class HospitalController : BaseController
    {
        // GET: Admin/Hospital
        public ActionResult Index()
        {
            if (!AdminId.HasValue)
                return RedirectToAction("Login", "Home");

            string response = GetAPICall("Users/GetHospitals");
            List<User> message = JsonConvert.DeserializeObject<List<User>>(response);

            return View("Index", message);
        }

        public ActionResult HospitalDetails(int id)
        {
            if (!AdminId.HasValue)
                return RedirectToAction("Login", "Home");

            string response = GetAPICall("Users/GetHospitalById?HospitalId=" + id);
            User message = (User)JsonConvert.DeserializeObject<User>(response);

            return View(message);
        }

        public ActionResult HospitalActive(int id, bool status)
        {
            if (!AdminId.HasValue)
                return RedirectToAction("Login", "Home");

            string response = GetAPICall("Users/UpdateUserActive?id=" + id + "&status=" + status);

            return RedirectToAction("Index", "Hospital");
        }

        public ActionResult HospitalByDoctors(int id)
        {
            if (!AdminId.HasValue)
                return RedirectToAction("Login", "Home");

            string response = GetAPICall("Users/GetDoctorsByHospital?HospitalId=" + id);
            List<User> message = JsonConvert.DeserializeObject<List<User>>(response);

           return View(message);
        }

        public ActionResult HospitalByPatients(int id)
        {
            if (!AdminId.HasValue)
                return RedirectToAction("Login", "Home");
            
            string response = GetAPICall("Users/GetPatientsByHospital?HospitalId=" + id);
            List<User> message = JsonConvert.DeserializeObject<List<User>>(response);

            return View(message);
        }
    }
}