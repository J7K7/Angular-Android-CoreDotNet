using EHealthCare.App_Start;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace EHealthCare.Areas.Admin.Controllers
{
    public class DoctorController : BaseController
    {
        // GET: Admin/Doctor
        public ActionResult Index()
        {
            if (!AdminId.HasValue)
                return RedirectToAction("Login", "Home");

            string response = GetAPICall("Users/GetDoctors");
            List<User> message = JsonConvert.DeserializeObject<List<User>>(response);

            return View("Index", message);
        }

        public ActionResult DoctorDetails(int id)
        {
            if (!AdminId.HasValue)
                return RedirectToAction("Login", "Home");

            string response = GetAPICall("Users/GetDoctorsById?DoctorId=" + id);
            User message = (User)JsonConvert.DeserializeObject<User>(response);

            return View(message);
        }

        public ActionResult DoctorByHospitals(int id)
        {
            if (!AdminId.HasValue)
                return RedirectToAction("Login", "Home");

            string response = GetAPICall("Users/GetHospitalsByDoctor?doctorId=" + id);
            List<User> message = JsonConvert.DeserializeObject<List<User>>(response);

            return View(message);
        }

        public ActionResult DoctorByPatients(int id)
        {
            if (!AdminId.HasValue)
                return RedirectToAction("Login", "Home");

            string response = GetAPICall("Users/GetPatientsByDoctor?doctorId=" + id);
            List<User> message = JsonConvert.DeserializeObject<List<User>>(response);

            return View(message);
        }
    }
}