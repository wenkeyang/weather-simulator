package au.com.weather_simulator.typing

case class ExtractBomSite(site_name: String, site_code: String, start_date: String, end_date: String) {
  override def toString: String =
    s"Site name: $site_name, Site Code: $site_code, Start Date: $start_date, End Date: $end_date"
}
