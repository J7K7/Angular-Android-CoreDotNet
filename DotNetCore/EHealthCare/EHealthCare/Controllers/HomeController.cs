using EHealthCare.App_Start;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
using System.Web.Http.Cors;

namespace EHealthCare.Controllers
{
    [EnableCors(origins: "*", headers: "*", methods: "*")]
    public class HomeController : BaseController
    {
        // GET: api/Home/GetCountries
        [HttpGet]
        public IHttpActionResult GetCountries()
        {
            List<Country> countries = (db.Countries.ToList()
                .Select(e => new Country
                {
                    Id = e.Id,
                    Name = e.Name
                }).ToList());

            return Ok(countries);
        }

        // GET: api/Home/GetStatesByCountryId?countryId=5
        [HttpGet]
        public IHttpActionResult GetStatesByCountryId(int countryId)
        {
            List<State> states = db.States.ToList().Where(e => e.CountryId == countryId)
                .Select(e => new State
                {
                    CountryId = e.CountryId,
                    Id = e.Id,
                    Name = e.Name
                }).ToList();

            return Ok(states);
        }

        // GET: api/Home/GetCityByStateId?stateId=5
        [HttpGet]
        public IHttpActionResult GetCityByStateId(int stateId)
        {
            List<City> cities = db.Cities.ToList().Where(e => e.StateId == stateId)
                .Select(e => new City
                {
                    StateId = e.StateId,
                    Id = e.Id,
                    Name = e.Name
                }).ToList();

            return Ok(cities);
        }
    }
}