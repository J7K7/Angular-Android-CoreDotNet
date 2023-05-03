using EHealthCare.App_Start;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace EHealthCare.Areas.Admin.Controllers
{
    public class PatientController : BaseController
    {
        // GET: Admin/Patient
        public ActionResult Index()
        {
            if (!AdminId.HasValue)
                return RedirectToAction("Login", "Home");

            string response = GetAPICall("Users/GetPatients");
            List<User> message = JsonConvert.DeserializeObject<List<User>>(response);

            return View("Index", message);
        }

        public ActionResult PatientsDetails(int id)
        {
            if (!AdminId.HasValue)
                return RedirectToAction("Login", "Home");

            string response = GetAPICall("Users/PatientDetails/" + id);
            User message = (User)JsonConvert.DeserializeObject<User>(response);

            return View(message);
        }

    }
}