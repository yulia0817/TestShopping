package com.ttp.shoppingapp.Model;

import java.util.List;

/**
 * Created by 0047TiTANplateform_ on 2018-03-21.
 */

public class MyResponse {
    public long multicast_id;
    public int success;
    public int failure;
    public int canonical_ids;
    public List<Result> results;
}
