import { City } from 'src/app/interface/city';
import { Country } from 'src/app/interface/country';
import { State } from 'src/app/interface/state';

export interface UserAddress {
    Id?: number;
    UserId?: number;
    Building?: string;
    Street?: string;
    CityId?: number;
    City?: City;
    Country?: Country;
    State?: State;
}
