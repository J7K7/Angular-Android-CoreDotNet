using EHealthCare.App_Start;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Web.Mvc;

namespace EHealthCare.Areas.Hospital.Controllers
{
    public class PatientController : BaseController
    {
        // GET: Hospital/Patient
        public ActionResult Index()
        {
            if (!HospitalId.HasValue)
                return RedirectToAction("Login", "Home");

            string response = GetAPICall("Users/GetPatientsByHospital?HospitalId=" + HospitalId);
            List<User> users = JsonConvert.DeserializeObject<List<User>>(response);

            return View("Index", users);
        }

        public ActionResult Detail()
        {
            return View();
        }
    }
}