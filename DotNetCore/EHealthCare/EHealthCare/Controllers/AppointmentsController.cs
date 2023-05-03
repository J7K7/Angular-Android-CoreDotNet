using EHealthCare.App_Start;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace EHealthCare.Controllers
{
    public class AppointmentsController : BaseController
    {
        [HttpPost]
        public IHttpActionResult AddAppointment(Appointment appointment)
        {
            try
            {
                db.Appointments.Add(appointment);
                db.SaveChanges();
                return Ok("Appintment Booked...!!!");
            }
            catch(Exception ex)
            {
                return Ok("Please try again later...!!!");
            }
        }
    }
}
