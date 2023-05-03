using EHealthCare.App_Start;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace EHealthCare.Controllers
{
    public class BaseController : ApiController
    {
        protected EHealthCareEntities db = new EHealthCareEntities();
    }
}