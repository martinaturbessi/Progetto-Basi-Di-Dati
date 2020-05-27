package Entity;

public class Filamento {
	private int idfil;
	private String name;
	private double totalFlux;
	private double meanDens;
	private double meanTemp;
	private float ellipticity;
	private float contrast;
	private String satellite;
	private String instrument;
	private double latCentroide;
	private double longCentroide;
	private double estensioneLat;
	private double estensioneLong;
	private int numeroSegmenti;


	public Filamento(int idfil) {

	}

	public Filamento()	{
		this.idfil = -1;
		this.name = "";
		this.latCentroide = -1;
		this.longCentroide = -1;
		this.estensioneLat = -1;
		this.estensioneLong = -1;
		this.numeroSegmenti = -1;
	}

	public String getName()	{
		return name;
	}

	public void setName(String name)	{
		this.name = name;
	}

	public double getTotalFlux()	{
		return totalFlux;
	}

	public void setTotalFlux(double totalFlux)	{
		this.totalFlux = totalFlux;
	}

	public double getMeanDens()	{
		return meanDens;
	}

	public void setMeanDens(double meanDens)	{
		this.meanDens = meanDens;
	}

	public double getMeanTemp()	{
		return meanTemp;
	}

	public void setMeanTemp(double meanTemp)	{
		this.meanTemp = meanTemp;
	}

	public float getEllipticity()	{
		return ellipticity;
	}

	public void setEllipticity(float ellipticity)	{
		this.ellipticity = ellipticity;
	}

	public float getContrast()	{
		return contrast;
	}

	public void setContrast(float contrast)	{
		this.contrast = contrast;
	}

	public String getSatellite()	{
		return satellite;
	}

	public void setSatellite(String satellite)	{
		this.satellite = satellite;
	}

	public String getInstrument()	{
		return instrument;
	}

	public void setInstrument(String instrument)	{
		this.instrument = instrument;
	}

	public double getLatCentroide() {
		return latCentroide;
	}

	public double getLongCentroide() {
		return longCentroide;
	}

	public int getIdfil() {
		return idfil;
	}

	public double getEstensioneLat() {
		return estensioneLat;
	}

	public double getEstensioneLong() {
		return estensioneLong;
	}

	public void setIdfil(int idfil) {
		this.idfil = idfil;
	}

	public void setLatCentroide(double latCentroide) {
		this.latCentroide = latCentroide;
	}

	public void setLongCentroide(double longCentroide) {
		this.longCentroide = longCentroide;
	}

	public void setEstensioneLat(double estensioneLat) {
		this.estensioneLat = estensioneLat;
	}

	public void setEstensioneLong(double estensioneLong) {
		this.estensioneLong = estensioneLong;
	}

	public int getNumeroSegmenti()	{
		return numeroSegmenti;
	}

	public void setNumeroSegmenti(int numeroSegmenti)	{
		this.numeroSegmenti = numeroSegmenti;
	}
}
