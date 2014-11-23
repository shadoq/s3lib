#ifdef GL_ES 
		#define LOWP lowp
		#define MED mediump
		#define HIGH highp
		precision mediump float;
	#else
		#define MED
		#define LOWP
#endif

#if defined(specularTextureFlag) || defined(specularColorFlag)
	#define specularFlag
#endif

#if defined(specularFlag) || defined(fogFlag)
	#define cameraPositionFlag
#endif

varying vec4 v_position;

#ifdef normalFlag
	varying vec3 v_normal;
#endif

#if defined(colorFlag)
	varying vec4 v_color;
#endif

#ifdef blendedFlag
		varying float v_opacity;
	#ifdef alphaTestFlag
		varying float v_alphaTest;
	#endif
#endif

#if defined(diffuseTextureFlag) || defined(specularTextureFlag)
	#define textureFlag
	varying MED vec2 v_texCoords0;
#endif

#ifdef diffuseColorFlag
	uniform vec4 u_diffuseColor;
#endif

#ifdef diffuseTextureFlag
	uniform sampler2D u_diffuseTexture;
#endif

#ifdef specularColorFlag
	uniform vec4 u_specularColor;
#endif

#ifdef specularTextureFlag
	uniform sampler2D u_specularTexture;
#endif

#ifdef normalTextureFlag
	uniform sampler2D u_normalTexture;
#endif

#ifdef cameraPositionFlag
	varying vec4 v_cameraPosition;
#endif

#ifdef fogFlag
	uniform vec4 u_fogColor;
	varying float v_fog;
#endif

#ifdef shininessFlag
		uniform float u_shininess;
	#else
		const float u_shininess = 20.0;
#endif

#ifdef lightingFlag

	#ifdef ambientLightFlag
		uniform vec3 u_ambientLight;
	#endif

	#ifdef ambientCubemapFlag
		uniform vec3 u_ambientCubemap[6];
	#endif

	#ifdef sphericalHarmonicsFlag
		uniform vec3 u_sphericalHarmonics[9];
	#endif

	#if defined(numDirectionalLights) && (numDirectionalLights > 0)
		struct DirectionalLight
		{
			vec3 color;
			vec3 direction;
		};
		uniform DirectionalLight u_dirLights[numDirectionalLights];
	#endif

	#if defined(numPointLights) && (numPointLights > 0)
		struct PointLight
		{
			vec3 color;
			vec3 position;
			float intensity;
		};
		uniform PointLight u_pointLights[numPointLights];
	#endif
#endif

void main() {
	#if defined(diffuseTextureFlag) && defined(diffuseColorFlag) && defined(colorFlag)
		vec4 diffuse = texture2D(u_diffuseTexture, v_texCoords0) * u_diffuseColor * v_color;
	#elif defined(diffuseTextureFlag) && defined(diffuseColorFlag)
		vec4 diffuse = texture2D(u_diffuseTexture, v_texCoords0) * u_diffuseColor;
	#elif defined(diffuseTextureFlag) && defined(colorFlag)
		vec4 diffuse = texture2D(u_diffuseTexture, v_texCoords0) * v_color;
	#elif defined(diffuseTextureFlag)
		vec4 diffuse = texture2D(u_diffuseTexture, v_texCoords0);
	#elif defined(diffuseColorFlag) && defined(colorFlag)
		vec4 diffuse = u_diffuseColor * v_color;
	#elif defined(diffuseColorFlag)
		vec4 diffuse = u_diffuseColor;
	#elif defined(colorFlag)
		vec4 diffuse = v_color;
	#else
		vec4 diffuse = vec4(1.0);
	#endif
	
	#if (defined(lightingFlag)) 
	
		#ifdef ambientLightFlag
			vec3 lightDiffuse = u_ambientLight;
		#else
			vec3 lightDiffuse = vec3(0.0);
		#endif
	
		#ifdef ambientCubemapFlag 		
			vec3 squaredNormal = v_normal * v_normal;
			vec3 isPositive  = step(0.0, v_normal);
			lightDiffuse += squaredNormal.x * mix(u_ambientCubemap[0], u_ambientCubemap[1], isPositive.x) +
					squaredNormal.y * mix(u_ambientCubemap[2], u_ambientCubemap[3], isPositive.y) +
					squaredNormal.z * mix(u_ambientCubemap[4], u_ambientCubemap[5], isPositive.z);
		#endif
		
		#ifdef sphericalHarmonicsFlag
			lightDiffuse += u_sphericalHarmonics[0];
			lightDiffuse += u_sphericalHarmonics[1] * v_normal.x;
			lightDiffuse += u_sphericalHarmonics[2] * v_normal.y;
			lightDiffuse += u_sphericalHarmonics[3] * v_normal.z;
			lightDiffuse += u_sphericalHarmonics[4] * (v_normal.x * v_normal.z);
			lightDiffuse += u_sphericalHarmonics[5] * (v_normal.z * v_normal.y);
			lightDiffuse += u_sphericalHarmonics[6] * (v_normal.y * v_normal.x);
			lightDiffuse += u_sphericalHarmonics[7] * (3.0 * v_normal.z * v_normal.z - 1.0);
			lightDiffuse += u_sphericalHarmonics[8] * (v_normal.x * v_normal.x - v_normal.y * v_normal.y);			
		#endif
		
		#ifdef specularFlag
			vec3 lightSpecular = vec3(0.0);
			vec3 viewVec = normalize(v_cameraPosition.xyz - v_position.xyz);
		#endif
		
		#if defined(numDirectionalLights) && (numDirectionalLights > 0) && defined(normalFlag)
			for (int i = 0; i < numDirectionalLights; i++) {
				vec3 lightDir = -u_dirLights[i].direction;
				float NdotL = clamp(dot(v_normal, lightDir), 0.0, 1.0);
				lightDiffuse += u_dirLights[i].color * NdotL;
				
				#ifdef specularFlag
					float halfDotView = dot(v_normal, normalize(lightDir + viewVec));
					lightSpecular += u_dirLights[i].color * clamp(NdotL * pow(halfDotView, u_shininess), 0.0, 1.0);
				#endif
			}
		#endif
		
		#if defined(numPointLights) && (numPointLights > 0) && defined(normalFlag)
			for (int i = 0; i < numPointLights; i++) {
				vec3 lightDir = u_pointLights[i].position - v_position.xyz;
				float dist2 = dot(lightDir, lightDir);
				lightDir *= inversesqrt(dist2);
				float NdotL = clamp(dot(v_normal, lightDir), 0.0, 2.0);
				float falloff = clamp(u_pointLights[i].intensity / (1.0 + dist2), 0.0, 2.0); // FIXME mul intensity on cpu
				lightDiffuse += u_pointLights[i].color * (NdotL * falloff);
				#ifdef specularFlag
					float halfDotView = clamp(dot(v_normal, normalize(lightDir + viewVec)), 0.0, 2.0);
					lightSpecular += u_pointLights[i].color * clamp(NdotL * pow(halfDotView, u_shininess) * falloff, 0.0, 2.0);
				#endif
			}
		#endif		
		
		#if (defined(specularFlag))
		
			#if defined(specularTextureFlag) && defined(specularColorFlag)
				vec3 specular = texture2D(u_specularTexture, v_texCoords0).rgb * u_specularColor.rgb * lightSpecular;
			#elif defined(specularTextureFlag)
				vec3 specular = texture2D(u_specularTexture, v_texCoords0).rgb * lightSpecular;
			#elif defined(specularColorFlag)
				vec3 specular = u_specularColor.rgb * lightSpecular;
			#else
				vec3 specular = lightSpecular;
			#endif
			
			gl_FragColor.rgb = (diffuse.rgb * lightDiffuse) + specular;
		#else
			gl_FragColor.rgb = (diffuse.rgb * lightDiffuse);
		#endif
	#else
		gl_FragColor.rgb = diffuse.rgb;
	#endif
	
	#ifdef fogFlag
    	gl_FragColor.rgb = mix(gl_FragColor.rgb, u_fogColor.rgb, v_fog);
    #endif

	#ifdef blendedFlag
		gl_FragColor.a = diffuse.a * v_opacity;
		#ifdef alphaTestFlag
			if (gl_FragColor.a <= v_alphaTest)
				discard;
		#endif
	#endif

}
