package dk.nielshvid.intermediary;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Shield {
	private PolicyHandler policyHandler = new PolicyHandler();
	private CapabilityHandler capabilityHandler = new CapabilityHandler();
	private InformationServiceInterface informationService = new InformationService();

	public Shield(){}
	public Shield(InformationServiceInterface informationService, HashSet<String> rolePolicyFreeResources,
				  HashSet<String> capabilityRequiringResources, HashMap<String, HashSet> entityPolicyRequiringResources,
				  PolicyHandler policyHandler, CapabilityHandler capabilityHandler){
		if(informationService != null){this.informationService = informationService;}
		if(rolePolicyFreeResources != null){
			Shield.rolePolicyFreeResources = rolePolicyFreeResources;}
		if(capabilityRequiringResources != null){
			Shield.capabilityRequiringResources = capabilityRequiringResources;}
		if(entityPolicyRequiringResources != null){
			Shield.entityPolicyRequiringResources = entityPolicyRequiringResources;}
		if(policyHandler != null){
			this.policyHandler = policyHandler;
		}
		if(capabilityHandler != null){
			this.capabilityHandler = capabilityHandler;
		}
	}

	private static HashSet<String> rolePolicyFreeResources = new HashSet<String>() {{
		add("FFU/authorize");
	}};
	
	private static HashSet<String> capabilityRequiringResources = new HashSet<String>() {{
        add("Freezer/retrieve");
	}};
	
	private static HashMap<String, HashSet> entityPolicyRequiringResources = new HashMap<String, HashSet>(){{
        put("Sample", new HashSet<String>(){{
        	add("Freezer/retrieve");
        }});
	}};
	
	public UUID generateCapability(String resource, MultivaluedMap<String, String> QPmap, String body, Boolean POST){

		String role;
		
		if (POST) {
			role = getRoleByOrganization(QPmap.getFirst("UserID"), QPmap.getFirst("OrganizationID"));
		} else {
			role = getRoleByEntity(QPmap.getFirst("UserID"), QPmap.getFirst("EntityID"));
		}

		if(!rolePolicyFreeResources.contains(resource)){
			if(!policyHandler.roleAuthorize(role, resource, QPmap, body)){
				return null;
			}
		}
		
		if(capabilityRequiringResources.contains(resource)){
			return null;
		}
		
		return capabilityHandler.addCapability(QPmap.getFirst("UserID"), resource);
	}
	
	public boolean postAuthorize(String resource, MultivaluedMap<String, String> QPmap, String body){
		String role = QPmap.getFirst("Role");
		String UserID = QPmap.getFirst("UserID");
				
		// Check role policy
		if(!rolePolicyFreeResources.contains(resource)){
			if(!policyHandler.roleAuthorize(role, resource, QPmap, body)){
				throw new WebApplicationException("Permission denied", Response.Status.FORBIDDEN);
			}
		}

		
		// Check capability
		if(capabilityRequiringResources.contains(resource)){
			String tempCapID = QPmap.getFirst("CapabilityID");
			if (UserID == null || tempCapID == null){
				throw new WebApplicationException("Missing input", Response.Status.BAD_REQUEST);
			}
		
			UUID CapabilityID = UUID.fromString(tempCapID);
			if(!capabilityHandler.authorize(UserID, CapabilityID, resource)){
				throw new WebApplicationException("Invalid capability", Response.Status.FORBIDDEN);
			}
		}
		return true;
	}

	public boolean authorize(String resource, MultivaluedMap<String, String> QPmap, String body){

		String UserID = QPmap.getFirst("UserID");
		String EntityID = QPmap.getFirst("EntityID");
		String role = getRoleByEntity(UserID, EntityID);

		// Check role policy
		if(!rolePolicyFreeResources.contains(resource)){
			if(!policyHandler.roleAuthorize(role, resource, QPmap, body)){
				throw new WebApplicationException("RP: Permission denied", Response.Status.FORBIDDEN);
			}
		}
		
		// Check entity policy
		String entityType = informationService.getEntityType(QPmap);
		if(entityPolicyRequiringResources.get(entityType).contains(resource)){
			if(!policyHandler.entityAuthorize(entityType, resource, QPmap)){
				throw new WebApplicationException("EP: Permission denied", Response.Status.FORBIDDEN);
			}
		}
		
		// Check capability
		if(capabilityRequiringResources.contains(resource)){
			String tempCapID = QPmap.getFirst("CapabilityID");
			if (UserID == null || tempCapID == null){
				throw new WebApplicationException("OP: Missing input", Response.Status.BAD_REQUEST);
			}
		
			UUID CapabilityID = UUID.fromString(tempCapID);
			if(!capabilityHandler.authorize(UserID, CapabilityID, resource)){
				throw new WebApplicationException("OP: Invalid capability", Response.Status.FORBIDDEN);
			}
		}
		return true;
	}

	private boolean _authorize(String resource, MultivaluedMap<String, String> QPmap, String body, String role){
		String UserID = QPmap.getFirst("UserID");
		String EntityID = QPmap.getFirst("EntityID");

		// Check role policy
		if(!rolePolicyFreeResources.contains(resource)){
			if(!policyHandler.roleAuthorize(role, resource, QPmap, body)){
				throw new WebApplicationException("Permission denied", Response.Status.FORBIDDEN);
			}
		}

		// Check entity policy
		String entityType = informationService.getEntityType(QPmap);
		if(entityPolicyRequiringResources.get(entityType).contains(resource)){
			if(!policyHandler.entityAuthorize(entityType, resource, QPmap)){
				throw new WebApplicationException("Permission denied", Response.Status.FORBIDDEN);
			}
		}

		// Check capability
		if(capabilityRequiringResources.contains(resource)){
			String tempCapID = QPmap.getFirst("CapabilityID");
			if (UserID == null || tempCapID == null){
				throw new WebApplicationException("Missing input", Response.Status.BAD_REQUEST);
			}

			UUID CapabilityID = UUID.fromString(tempCapID);
			if(!capabilityHandler.authorize(UserID, CapabilityID, resource)){
				throw new WebApplicationException("Invalid capability", Response.Status.FORBIDDEN);
			}
		}
		return true;
	}

	private String getRoleByOrganization(String UserID, String OrganizationID){
		String role = "";
		try {
			role = informationService.getRoleByOrganization(UserID, OrganizationID);
		} catch (Exception e){
			throw new WebApplicationException("Invalid User ID", Response.Status.BAD_REQUEST);
		}
		return role;
	}


	private String getRoleByEntity(String UserID, String EntityID){
		String role = "";
		try {
			role = informationService.getRoleByEntity(UserID, EntityID);
		} catch (Exception e){
			throw new WebApplicationException("Invalid User ID", Response.Status.BAD_REQUEST);
		}
		return role;
	}
}
