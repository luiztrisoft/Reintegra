package br.com.bean;

import br.com.controller.Data;

/**
 * Classe que é responsável pela modelagem do interno.
 * 
 * @author Luiz Alberto
 * 
 */
public class InternoBean extends PessoaFisica {

	private int contribuicao;
	private int status;
	private int convenio;
	// private int tipodeVicio;
	private int dependencia;

	private Data dataAdmissao = new Data();
	private Data dataSaida = new Data();

	private String tipoSaida;
	private String motivoSaida;

	private String processoCriminal;
	private String local;
	private String sofreAmeaca;
	private String tempodeUso;
	private String motivodeUso;
	private String diabetico;
	private String contraiuDoenca;
	private String usaMedicacao;
	private String recomendacaoMedica;
	private String documentosEntregues;

	public int getContribuicao() {
		return contribuicao;
	}

	public void setContribuicao(int contribuicao) {
		this.contribuicao = contribuicao;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getConvenio() {
		return convenio;
	}

	public void setConvenio(int convenio) {
		this.convenio = convenio;
	}

	public String getTipoSaida() {
		return tipoSaida;
	}

	public void setTipoSaida(String tipoSaida) {
		this.tipoSaida = tipoSaida;
	}

	public String getMotivoSaida() {
		return motivoSaida;
	}

	public void setMotivoSaida(String motivoSaida) {
		this.motivoSaida = motivoSaida;
	}

	public String getProcessoCriminal() {
		return processoCriminal;
	}

	public void setProcessoCriminal(String processoCriminal) {
		this.processoCriminal = processoCriminal;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getSofreAmeaca() {
		return sofreAmeaca;
	}

	public void setSofreAmeaca(String sofreAmeaca) {
		this.sofreAmeaca = sofreAmeaca;
	}

	public int getdependencia() {
		return dependencia;
	}

	public void setDependencia(int dependencia) {
		this.dependencia = dependencia;
	}

	public String getTempodeUso() {
		return tempodeUso;
	}

	public void setTempodeUso(String tempodeUso) {
		this.tempodeUso = tempodeUso;
	}

	public String getMotivodeUso() {
		return motivodeUso;
	}

	public void setMotivodeUso(String motivodeUso) {
		this.motivodeUso = motivodeUso;
	}

	public String getDiabetico() {
		return diabetico;
	}

	public void setDiabetico(String diabetico) {
		this.diabetico = diabetico;
	}

	public String getContraiuDoenca() {
		return contraiuDoenca;
	}

	public void setContraiuDoenca(String contraiuDoenca) {
		this.contraiuDoenca = contraiuDoenca;
	}

	public String getUsaMedicacao() {
		return usaMedicacao;
	}

	public void setUsaMedicacao(String usaMedicacao) {
		this.usaMedicacao = usaMedicacao;
	}

	public String getRecomendacaoMedica() {
		return recomendacaoMedica;
	}

	public void setRecomendacaoMedica(String recomendacaoMedica) {
		this.recomendacaoMedica = recomendacaoMedica;
	}

	public String getDocumentosEntregues() {
		return documentosEntregues;
	}

	public void setDocumentosEntregues(String documentosEntregues) {
		this.documentosEntregues = documentosEntregues;
	}

	public Data getDataAdmissao() {
		return dataAdmissao;
	}

	public Data getDataSaida() {
		return dataSaida;
	}

}
